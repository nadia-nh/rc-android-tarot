package com.example.simpletarot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletarot.AppScreen
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.ReadingWithCards
import com.example.simpletarot.data.repository.TarotRepository
import com.example.simpletarot.data.local.toEntity
import com.example.simpletarot.domain.model.TarotCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.random.Random

class TarotViewModel(private val repository: TarotRepository) : ViewModel() {
    private val _tarotDeck = MutableStateFlow<List<TarotCard>>(emptyList())
    private val _currentSpread = MutableStateFlow<List<DrawnCard>>(emptyList())
    private val _currentScreen = MutableStateFlow(AppScreen.Menu)
    private val _isSaved = MutableStateFlow(false)
    private val _pendingDeletion = MutableStateFlow<ReadingEntity?>(null)

    val currentSpread: StateFlow<List<DrawnCard>> = _currentSpread
    val currentScreen: StateFlow<AppScreen> = _currentScreen
    val isSaved: StateFlow<Boolean> = _isSaved
    val pendingDeletion: StateFlow<ReadingEntity?> = _pendingDeletion

    val isRevealed: StateFlow<Boolean> = _currentSpread
        .map { cards -> cards.all { it.isRevealed } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val previousReadings: StateFlow<List<ReadingWithCards>> = repository.allReadings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            _tarotDeck.value = repository
                .getFullDeck(useNetwork = true)
        }
    }

    private fun draw(count: Int): List<DrawnCard> {
        return _tarotDeck.value
            .shuffled()
            .take(count)
            .map { card ->
                DrawnCard(
                    card = card,
                    isReversed = Random.nextBoolean(),
                    isRevealed = false
                )
            }
    }

    fun drawCards(count: Int) {
        _currentSpread.value = draw(count)
        _currentScreen.value = AppScreen.Result
        _isSaved.value = false
    }

    fun revealCard(cardIndex: Int) {
        val currentList = _currentSpread.value.toMutableList()

        if (cardIndex in currentList.indices) {
            currentList[cardIndex] = currentList[cardIndex].copy(isRevealed = true)
            _currentSpread.value = currentList
        }
    }

    fun clearSpread() {
        _currentSpread.value = emptyList()
        _currentScreen.value = AppScreen.Menu
    }

    fun backToMenu() {
        _currentScreen.value = AppScreen.Menu
    }

    fun openHistory() {
        _currentScreen.value = AppScreen.History
    }

    // Save current spread
    fun saveReading() {
        if (_isSaved.value) return

        val cardCount = _currentSpread.value.size
        val spreadType = if (cardCount == 3) "ThreeCardDraw" else "SingleCardDraw"
        val reading = ReadingEntity(spreadType = spreadType)
        val cards = _currentSpread.value.map {
            it.toEntity(reading.readingId)
        }

        viewModelScope.launch {
            repository.saveReading(reading, cards)
            _isSaved.value = true
        }
    }

    fun scheduleDeletion(reading: ReadingEntity) {
        _pendingDeletion.value = reading
    }

    fun cancelDeletion() {
        _pendingDeletion.value = null
    }

    fun confirmDeletion() {
        val reading = _pendingDeletion.value ?: return
        viewModelScope.launch {
            repository.deleteReading(reading)
            _pendingDeletion.value = null
        }
    }
}