package com.example.simpletarot

import DrawnCardEntity
import ReadingEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotDeck
import com.example.simpletarot.database.TarotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TarotViewModel(private val repository: TarotRepository) : ViewModel() {
    private val _currentSpread = MutableStateFlow<List<DrawnCard>>(emptyList())
    private val _currentScreen = MutableStateFlow(AppScreen.Menu)
    private val _isSaved = MutableStateFlow(false)

    val currentSpread: StateFlow<List<DrawnCard>> = _currentSpread
    val currentScreen: StateFlow<AppScreen> = _currentScreen
    val isSaved: StateFlow<Boolean> = _isSaved

    val isRevealed: StateFlow<Boolean> = _currentSpread
        .map { cards -> cards.all { it.isRevealed } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun drawCards(count: Int) {
        _currentSpread.value = TarotDeck.draw(count)
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

    // Save current spread
    fun saveReading() {
        if (_isSaved.value) return;

        val cardCount = _currentSpread.value.size
        val spreadType = if (cardCount == 3) "ThreeCardDraw" else "SingleCardDraw"
        val reading = ReadingEntity(spreadType = spreadType)
        val cards = _currentSpread.value.map {
            DrawnCardEntity(
                readingOwnerId = reading.readingId,
                name = it.card.name,
                isReversed = it.isReversed,
                suit = it.card.suit.name,
                rank = it.card.rank?.name
            )
        }

        viewModelScope.launch {
            repository.saveReading(reading, cards)
            _isSaved.value = true
        }
    }
}
