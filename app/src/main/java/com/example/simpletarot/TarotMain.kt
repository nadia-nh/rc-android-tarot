package com.example.simpletarot

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.screens.CardDetailScreen
import com.example.simpletarot.ui.screens.HistoryScreen
import com.example.simpletarot.ui.screens.MenuScreen
import com.example.simpletarot.ui.screens.ResultsScreen
import com.example.simpletarot.ui.components.TarotBottomBar
import com.example.simpletarot.ui.components.TarotHeadline
import com.example.simpletarot.ui.components.TarotNavigationRail
import com.example.simpletarot.ui.screens.CardDetailScreenTopBar
import com.example.simpletarot.ui.screens.HistoryScreenTopBar
import com.example.simpletarot.ui.screens.ResultScreenTopAppBar
import com.example.simpletarot.ui.theme.LocalSpacing
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
    val selectedScreen by viewModel.selectedScreen.collectAsState()
    val spread by viewModel.currentSpread.collectAsState()
    val selectedCard by viewModel.selectedCard.collectAsState()
    val dailySpread by viewModel.dailySpread.collectAsState()
    val cardCount = spread.size

    BackHandler(enabled = currentScreen == AppScreen.Result || currentScreen == AppScreen.CardDetail) {
        if (currentScreen == AppScreen.CardDetail) {
            viewModel.closeCardDetail()
        } else {
            viewModel.backToMenu()
        }
    }

    TarotMainInternal(
        viewModel = viewModel,
        isLandscape = isLandscape,
        currentScreen = currentScreen,
        dailySpread = dailySpread,
        currentSpread = spread,
        selectedCard = selectedCard,
        onHome = { viewModel.backToMenu() },
        onBack = { viewModel.backToMenu() },
        onSave = { viewModel.saveReading() },
        onOpenHistory = { viewModel.openHistory() },
        onCloseDetail = { viewModel.closeCardDetail() },
        onDraw = { count -> viewModel.drawCards(count) },
        onCardClick = { card -> viewModel.openCardDetail(card) },
        onReveal = { index -> viewModel.revealCard(index) },
        homeSelected = selectedScreen == AppScreen.Menu,
        oneCardSelected =
            (selectedScreen == AppScreen.Result) && cardCount == 1,
        threeCardsSelected =
            (selectedScreen == AppScreen.Result) && cardCount == 3,
        historySelected = selectedScreen == AppScreen.History
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotMainInternal(
    viewModel: TarotViewModel,
    isLandscape: Boolean = false,
    currentScreen: AppScreen = AppScreen.Menu,
    dailySpread: List<DrawnCard> = listOf(),
    currentSpread: List<DrawnCard> = listOf(),
    selectedCard: DrawnCard? = null,
    onHome: () -> Unit = {},
    onBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onOpenHistory: () -> Unit = {},
    onCloseDetail: () -> Unit = {},
    onDraw: (count: Int) -> Unit = {},
    onCardClick: (card: DrawnCard) -> Unit = {},
    onReveal: (index: Int) -> Unit = {},
    homeSelected: Boolean = true,
    oneCardSelected: Boolean = false,
    threeCardsSelected: Boolean = false,
    historySelected: Boolean = false
) {
    val spacing = LocalSpacing.current

    Row(modifier = Modifier.fillMaxSize()) {
        if (isLandscape) {
            TarotNavigationRail(
                onHome = onHome,
                onDraw = onDraw,
                onOpenHistory = onOpenHistory,
                homeSelected = homeSelected,
                oneCardSelected = oneCardSelected,
                threeCardsSelected = threeCardsSelected,
                historySelected = historySelected,
            )
        }

        Scaffold(
            topBar = {
                when (currentScreen) {
                    AppScreen.Menu ->
                        TopAppBar( title = {TarotHeadline(spacing)} )
                    AppScreen.Result ->
                        ResultScreenTopAppBar(currentSpread.size, onBack)
                    AppScreen.History -> HistoryScreenTopBar(onBack)
                    AppScreen.CardDetail  ->
                        selectedCard?.let {
                            CardDetailScreenTopBar(selectedCard, onCloseDetail)
                        }
                }
            },
            bottomBar = {
                if (!isLandscape) {
                    TarotBottomBar(
                        onHome = onHome,
                        onDraw = onDraw,
                        onOpenHistory = onOpenHistory,
                        homeSelected = homeSelected,
                        oneCardSelected = oneCardSelected,
                        threeCardsSelected = threeCardsSelected,
                        historySelected = historySelected,
                    )
                }
            }
        ) { innerPadding ->

            when (currentScreen) {
                AppScreen.Menu ->
                    MenuScreen(
                        isLandscape = isLandscape,
                        padding = innerPadding,
                        dailySpread = dailySpread,
                        onCardClick = onCardClick
                    )

                AppScreen.Result ->
                    ResultsScreen(
                        isLandscape = isLandscape,
                        padding = innerPadding,
                        cards = currentSpread,
                        isSaved = viewModel.isSaved,
                        allCardsRevealed = viewModel.isRevealed,
                        onSave = onSave,
                        onReveal = onReveal,
                        onCardClick = onCardClick
                    )

                AppScreen.History ->
                    HistoryScreen(
                        viewModel = viewModel,
                        padding = innerPadding
                    )

                AppScreen.CardDetail ->
                    selectedCard?.let { card ->
                        CardDetailScreen(
                            isLandscape = isLandscape,
                            padding = innerPadding,
                            drawnCard = card,
                        )
                    }
            }
        }
    }
}
