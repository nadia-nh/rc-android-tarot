package com.example.simpletarot

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import com.example.simpletarot.ui.screens.HistoryScreen
import com.example.simpletarot.ui.screens.MenuScreen
import com.example.simpletarot.ui.screens.ResultsScreen
import com.example.simpletarot.ui.viewmodel.TarotViewModel

enum class AppScreen {
    Menu,
    Result,
    History,
    CardDetail
}
@Composable
fun TarotMain(
    viewModel: TarotViewModel
) {
    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Intercept the hardware back button
    BackHandler(enabled = currentScreen == AppScreen.Result) {
        viewModel.clearSpread()
    }

    when (currentScreen) {
        AppScreen.Menu ->
            MenuScreen(
                isLandscape = isLandscape,
                onDraw = { count -> viewModel.drawCards(count) },
                onOpenHistory = { viewModel.openHistory() }
            )
        AppScreen.Result ->
            ResultsScreen(
                isLandscape = isLandscape,
                viewModel = viewModel
            ) {
                viewModel.clearSpread()
            }
        AppScreen.History ->
            HistoryScreen(
                isLandscape = isLandscape,
                viewModel = viewModel
            ) {
                viewModel.backToMenu()
            }
    }
}
