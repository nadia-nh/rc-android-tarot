package com.example.simpletarot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

enum class AppScreen {
    Menu,
    Result,
}
@Composable
fun TarotMain(viewModel: TarotViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    when (currentScreen) {
        AppScreen.Menu ->
            MenuScreen {
                count -> viewModel.drawCards(count)
            }
        AppScreen.Result ->
            ResultsScreen(viewModel) {
                viewModel.clearSpread()
            }
    }
}
