package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.CardDisplay
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.TarotSpacing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.simpletarot.data.PreviewConstants
import com.example.simpletarot.viewmodel.TarotViewModel

@Composable
fun ResultsScreen(
    isLandscape: Boolean = false,
    viewModel: TarotViewModel,
    onBack: () -> Unit = {}) {
    val spread by viewModel.currentSpread.collectAsState()
    ResultsScreenStateless(
        isLandscape = isLandscape,
        cards = spread,
        isSaved = viewModel.isSaved,
        allCardsRevealed = viewModel.isRevealed,
        onBack = onBack,
        onSave = { viewModel.saveReading() },
        onReveal = { index -> viewModel.revealCard(index) })
}

@Composable
fun ResultsScreenStateless(
    isLandscape: Boolean = false,
    cards : List<DrawnCard>,
    isSaved: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow(),
    allCardsRevealed: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow(),
    onBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onReveal: (index: Int) -> Unit = {}) {
    val cardCount = cards.size
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.medium))
        Text(text = if (cardCount == 1) "Your Card" else "Your Spread",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall)

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CardsDisplay(
                isLandscape = isLandscape,
                spacing = spacing,
                cards = cards,
                onReveal = onReveal)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SaveButton(
                spacing = spacing,
                isSaved.collectAsState().value,
                allCardsRevealed.collectAsState().value,
                onSave)
            Spacer(modifier = Modifier.size(spacing.medium))
            BackButton(onBack = onBack)
        }

        Spacer(modifier = Modifier.height(spacing.medium))
    }
}

@Composable
fun CardsDisplay(
    isLandscape: Boolean = false,
    spacing: TarotSpacing = LocalSpacing.current,
    cards: List<DrawnCard>,
    onReveal: (index: Int) -> Unit = {}) {
    if (isLandscape) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(horizontal = spacing.extraLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(cards) { index, card ->
                CardDisplay(
                    isLandscape = isLandscape,
                    drawnCard = card
                ) {
                    onReveal(index)
                }
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(vertical = spacing.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(cards) { index, card ->
                CardDisplay(
                    isLandscape = isLandscape,
                    drawnCard = card
                ) {
                    onReveal(index)
                }
            }
        }
    }
}

@Composable
fun SaveButton(
    spacing: TarotSpacing = LocalSpacing.current,
    isSaved: Boolean,
    allCardsRevealed: Boolean,
    onSave: () -> Unit = {}) {
    Button(
        onClick = onSave,
        enabled = allCardsRevealed && !isSaved) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSaved) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.size(spacing.small))
                Text(
                    "Saved",
                    color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Save Reading",
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun BackButton(onBack: () -> Unit = {}) {
    Button(onClick = onBack) {
        Text(
            "Back to Menu",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun CardsDisplayPreview() {
    CardsDisplay(cards = PreviewConstants.tarotCards)
}

@Preview
@Composable
fun SaveButtonPreview() {
    SaveButton(isSaved = false, allCardsRevealed = true)
}

@Preview
@Composable
fun BackButtonPreview() {
    BackButton()
}

@Preview
@Composable
fun ResultsScreenPreview() {
    ResultsScreenStateless(cards = PreviewConstants.tarotCards)
}