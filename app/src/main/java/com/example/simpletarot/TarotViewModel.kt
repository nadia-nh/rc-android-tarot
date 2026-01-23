package com.example.simpletarot

import androidx.lifecycle.ViewModel
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotDeck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TarotViewModel : ViewModel() {
    private val _currentSpread = MutableStateFlow<List<DrawnCard>>(emptyList())
    private val _currentScreen = MutableStateFlow(AppScreen.Menu)

    val currentSpread: StateFlow<List<DrawnCard>> = _currentSpread
    val currentScreen: StateFlow<AppScreen> = _currentScreen

    fun drawCards(count: Int) {
        _currentSpread.value = TarotDeck.draw(count)
        _currentScreen.value = AppScreen.Result
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
}
