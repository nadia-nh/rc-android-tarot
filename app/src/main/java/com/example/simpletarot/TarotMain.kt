package com.example.simpletarot

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.simpletarot.ui.screens.CardDetailScreen
import com.example.simpletarot.ui.screens.HistoryScreen
import com.example.simpletarot.ui.screens.MenuScreen
import com.example.simpletarot.ui.screens.MenuScreenBottomBar
import com.example.simpletarot.ui.screens.ResultsScreen
import com.example.simpletarot.ui.screens.TarotHeadline
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.viewmodel.TarotViewModel

enum class AppScreen {
    Menu,
    Result,
    History,
    CardDetail
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotMain(
    viewModel: TarotViewModel
) {
    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedCard by viewModel.selectedCard.collectAsState()
    val dailySpread by viewModel.dailySpread.collectAsState()
    val spacing = LocalSpacing.current

    BackHandler(enabled = currentScreen == AppScreen.Result || currentScreen == AppScreen.CardDetail) {
        if (currentScreen == AppScreen.CardDetail) {
            viewModel.closeCardDetail()
        } else {
            viewModel.clearSpread()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {TarotHeadline(spacing)}
            )
        },
        bottomBar = {
            MenuScreenBottomBar(
                onDraw = { count -> viewModel.drawCards(count) },
                onOpenHistory = { viewModel.openHistory() },
            )
        }
    ) { innerPadding ->

        Spacer(modifier = Modifier.padding(innerPadding))

        when (currentScreen) {
            AppScreen.Menu ->
                MenuScreen(
                    isLandscape = isLandscape,
                    dailySpread = dailySpread,
                    onCardClick = { card -> viewModel.openCardDetail(card) }
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
                    viewModel = viewModel
                ) {
                    viewModel.backToMenu()
                }

            AppScreen.CardDetail ->
                selectedCard?.let { card ->
                    CardDetailScreen(
                        drawnCard = card,
                        isLandscape = isLandscape,
                        onBack = { viewModel.closeCardDetail() }
                    )
                }
        }
    }
}
