package com.example.simpletarot

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
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.withRankAndSuit
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.TarotSpacing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ResultsScreen(
    viewModel: TarotViewModel,
    onBack: () -> Unit) {
    val spread by viewModel.currentSpread.collectAsState()
    ResultsScreenStateless(
        cards = spread,
        isSaved = viewModel.isSaved,
        allCardsRevealed = viewModel.isRevealed,
        onBack = onBack,
        onSave = { viewModel.saveReading() },
        onReveal = { index -> viewModel.revealCard(index) })
}

@Composable
fun ResultsScreenStateless(
    cards : List<DrawnCard>,
    isSaved: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow(),
    allCardsRevealed: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow(),
    onBack: () -> Unit,
    onSave: () -> Unit,
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
        ) { CardsDisplay(spacing = spacing, cards = cards, onReveal = onReveal) }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SaveButton(
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
    spacing: TarotSpacing = LocalSpacing.current,
    isLandscape: Boolean = false,
    cards: List<DrawnCard>,
    onReveal: (index: Int) -> Unit = {}) {
    if (isLandscape) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(horizontal = spacing.extraLarge),
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(cards) { index, card ->
                CardDisplay(card) {
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
                CardDisplay(card) {
                    onReveal(index)
                }
            }
        }
    }
}

@Composable
fun SaveButton(isSaved: Boolean, allCardsRevealed: Boolean, onSave: () -> Unit = {}) {
    Button(
        onClick = onSave,
        enabled = allCardsRevealed && !isSaved) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSaved) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.size(8.dp))
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
            "New Reading",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun CardsDisplayPreview() {
    val cards = listOf(
        TarotCard("Knight of Pentacles",
            "Reliability, hard work, responsibility",
            "Stagnation, boredom, laziness"),
        TarotCard("Nine of Swords",
            "Anxiety, guilt, worry",
            "Hope, comfort, letting go of fear"),
        TarotCard("Two of Cups",
            "Connection, partnership, attraction",
            "Breakup, imbalance, tension"),
    ).map { card ->
        DrawnCard(card = card.withRankAndSuit(),
            isReversed = false,
            isRevealed = true)
    }
    CardsDisplay(cards = cards)
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
    val cards = listOf(
        TarotCard("Knight of Pentacles",
            "Reliability, hard work, responsibility",
            "Stagnation, boredom, laziness"),
        TarotCard("Nine of Swords",
            "Anxiety, guilt, worry",
            "Hope, comfort, letting go of fear"),
        TarotCard("Two of Cups",
            "Connection, partnership, attraction",
            "Breakup, imbalance, tension"),
        ).map { card ->
            DrawnCard(card = card.withRankAndSuit(),
                isReversed = false,
                isRevealed = true)
        }
    ResultsScreenStateless(cards = cards, onSave = {}, onBack = {})
}