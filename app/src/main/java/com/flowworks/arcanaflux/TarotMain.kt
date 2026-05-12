package com.flowworks.arcanaflux

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.flowworks.arcanaflux.data.local.DrawnCardEntity
import com.flowworks.arcanaflux.data.local.ReadingEntity
import com.flowworks.arcanaflux.data.local.ReadingWithCards
import com.flowworks.arcanaflux.data.local.toDrawnCard
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.ui.components.DeleteConfirmationDialog
import com.flowworks.arcanaflux.ui.screens.CardDetailScreen
import com.flowworks.arcanaflux.ui.screens.HistoryScreen
import com.flowworks.arcanaflux.ui.screens.MenuScreen
import com.flowworks.arcanaflux.ui.screens.ResultsScreen
import com.flowworks.arcanaflux.ui.components.TarotBottomBar
import com.flowworks.arcanaflux.ui.components.TarotHeadline
import com.flowworks.arcanaflux.ui.components.TarotNavigationRail
import com.flowworks.arcanaflux.ui.screens.CardDetailScreenTopBar
import com.flowworks.arcanaflux.ui.screens.HistoryScreenTopBar
import com.flowworks.arcanaflux.ui.screens.ResultScreenTopAppBar
import com.flowworks.arcanaflux.ui.theme.LocalSpacing
import com.flowworks.arcanaflux.ui.viewmodel.TarotViewModel

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
    val pendingDeletion by viewModel.pendingDeletion.collectAsState()
    val history by viewModel.previousReadings.collectAsState()
    val isSpreadSaved by viewModel.isSpreadSaved.collectAsState()
    val allRevealed by viewModel.isSpreadRevealed.collectAsState()

    BackHandler(enabled = currentScreen == AppScreen.Result || currentScreen == AppScreen.CardDetail) {
        if (currentScreen == AppScreen.CardDetail) {
            viewModel.closeCardDetail()
        } else {
            viewModel.backToMenu()
        }
    }

    TarotMainInternal(
        isLandscape = isLandscape,
        currentScreen = currentScreen,
        selectedScreen = selectedScreen,
        dailySpread = dailySpread,
        currentSpread = spread,
        selectedCard = selectedCard,
        pendingDeletion = pendingDeletion,
        history = history,
        isSpreadSaved = isSpreadSaved,
        allRevealed = allRevealed,
        onHome = { viewModel.backToMenu() },
        onBack = { viewModel.backToMenu() },
        onSave = { viewModel.saveReading() },
        onOpenHistory = { viewModel.openHistory() },
        onCloseDetail = { viewModel.closeCardDetail() },
        onDraw = { count -> viewModel.drawCards(count) },
        onCardClick = { card -> viewModel.openCardDetail(card) },
        onReveal = { index -> viewModel.revealCard(index) },
        resolveCard = { cardEntity -> viewModel.resolveCard(cardEntity) },
        scheduleDeletion = { entity -> viewModel.scheduleDeletion(entity) },
        confirmDeletion = { viewModel.confirmDeletion() },
        cancelDeletion = { viewModel.cancelDeletion() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotMainInternal(
    isLandscape: Boolean = false,
    currentScreen: AppScreen = AppScreen.Menu,
    selectedScreen: AppScreen = AppScreen.Menu,
    dailySpread: List<DrawnCard> = listOf(),
    currentSpread: List<DrawnCard> = listOf(),
    selectedCard: DrawnCard? = null,
    pendingDeletion: ReadingEntity? = null,
    history: List<ReadingWithCards> = listOf(),
    isSpreadSaved: Boolean = false,
    allRevealed: Boolean = false,
    onHome: () -> Unit = {},
    onBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onOpenHistory: () -> Unit = {},
    onCloseDetail: () -> Unit = {},
    onDraw: (count: Int) -> Unit = {},
    onCardClick: (card: DrawnCard) -> Unit = {},
    onReveal: (index: Int) -> Unit = {},
    resolveCard: (card: DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() },
    scheduleDeletion: (entity: ReadingEntity) -> Unit = {},
    confirmDeletion: () -> Unit = {},
    cancelDeletion: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val cardCount = currentSpread.size
    val homeSelected = selectedScreen == AppScreen.Menu
    val resultSelected = selectedScreen == AppScreen.Result
    val historySelected = selectedScreen == AppScreen.History
    val oneCardSelected = resultSelected && cardCount == 1
    val threeCardsSelected = resultSelected && cardCount == 3

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
                        ResultScreenTopAppBar(cardCount, onBack)
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
                        modifier = Modifier.padding(innerPadding),
                        isLandscape = isLandscape,
                        dailySpread = dailySpread,
                        onCardClick = onCardClick
                    )

                AppScreen.Result ->
                    ResultsScreen(
                        modifier = Modifier.padding(innerPadding),
                        isLandscape = isLandscape,
                        cards = currentSpread,
                        isSaved = isSpreadSaved,
                        allCardsRevealed = allRevealed,
                        onSave = onSave,
                        onReveal = onReveal,
                        onCardClick = onCardClick
                    )

                AppScreen.History -> {
                    pendingDeletion?.let { _ ->
                        DeleteConfirmationDialog(
                            onConfirm = confirmDeletion,
                            onDismiss = cancelDeletion
                        )
                    }
                    HistoryScreen(
                        modifier = Modifier.padding(innerPadding),
                        history = history,
                        onCardClick = onCardClick,
                        onDeleteRequest = scheduleDeletion,
                        resolveCard = resolveCard
                    )
                }

                AppScreen.CardDetail ->
                    selectedCard?.let { card ->
                        CardDetailScreen(
                            drawnCard = card,
                            modifier = Modifier.padding(innerPadding),
                            isLandscape = isLandscape,
                        )
                    }
            }
        }
    }
}
