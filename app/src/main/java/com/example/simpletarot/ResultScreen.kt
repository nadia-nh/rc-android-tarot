package com.example.simpletarot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.withRankAndSuit
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun ResultsScreen(
    viewModel: TarotViewModel,
    onBack: () -> Unit) {
    val spread by viewModel.currentSpread.collectAsState()
    ResultsScreenStateless(
        cards = spread,
        onBack = onBack) {
        index -> viewModel.revealCard(index)
    }
}

@Composable
fun ResultsScreenStateless(
    cards : List<DrawnCard>,
    onBack: () -> Unit,
    onReveal: (index: Int) -> Unit = {}) {
    val cardCount = cards.size
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (cardCount == 1) "Your Card" else "Your Spread",
            style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
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
        }

        Button(onClick = onBack) {
            Text("New Reading")
        }
    }
}

@Preview
@Composable
fun ResultsScreenPreview() {
    val cards = listOf(
        TarotCard("Knight of Pentacles",
            "reliability, hard work, responsibility",
            "stagnation, boredom, laziness"),
        TarotCard("Nine of Swords",
            "anxiety, guilt, worry",
            "hope, comfort, letting go of fear"),
        TarotCard("Two of Cups",
            "connection, partnership, attraction",
            "breakup, imbalance, tension"),
        ).map { card ->
            DrawnCard(card = card.withRankAndSuit(),
                isReversed = false,
                isRevealed = true)
        }
    ResultsScreenStateless(cards = cards, onBack = {})
}