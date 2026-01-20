package com.example.simpletarot

import androidx.lifecycle.ViewModel
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotDeck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TarotViewModel : ViewModel() {
    private val _currentSpread = MutableStateFlow<List<DrawnCard>>(emptyList())
    val currentSpread: StateFlow<List<DrawnCard>> = _currentSpread

    fun drawCards(count: Int) {
        _currentSpread.value = TarotDeck.draw(count)
    }

    fun clearSpread() {
        _currentSpread.value = emptyList()
    }
}
