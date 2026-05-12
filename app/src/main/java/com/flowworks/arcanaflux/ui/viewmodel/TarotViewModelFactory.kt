package com.flowworks.arcanaflux.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flowworks.arcanaflux.data.repository.TarotRepository

class TarotViewModelFactory(private val repository: TarotRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TarotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TarotViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}