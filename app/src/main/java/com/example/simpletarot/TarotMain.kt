package com.example.simpletarot

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration

enum class AppScreen {
    Menu,
    Result,
}
@Composable
fun TarotMain(
    viewModel: TarotViewModel) {
    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Intercept the hardware back button
    BackHandler(enabled = currentScreen == AppScreen.Result) {
        viewModel.clearSpread()
    }

    when (currentScreen) {
        AppScreen.Menu ->
            MenuScreen {
                count -> viewModel.drawCards(count)
            }
        AppScreen.Result ->
            ResultsScreen(
                isLandscape = isLandscape,
                viewModel = viewModel) {
                viewModel.clearSpread()
            }
    }
}
