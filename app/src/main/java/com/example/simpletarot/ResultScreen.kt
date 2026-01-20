package com.example.simpletarot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.withClassification

@Composable
fun ResultsScreen(viewModel: TarotViewModel, onBack: () -> Unit) {
    val spread by viewModel.currentSpread.collectAsState()
    ResultsScreenStateless(cards = spread, onBack = onBack)
}

@Composable
fun ResultsScreenStateless(cards : List<DrawnCard>, onBack: () -> Unit) {
    val cardCount = cards.size

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (cardCount == 1) "Your Card" else "Your Spread",
            style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(24.dp))

        // Display cards horizontally
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            items(cards) { card ->
                CardDisplay(card)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onBack) {
            Text("Draw Again")
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
            DrawnCard(card = card.withClassification(), isReversed = false)
        }
    ResultsScreenStateless(cards = cards, onBack = {})
}