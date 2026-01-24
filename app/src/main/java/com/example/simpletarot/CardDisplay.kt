package com.example.simpletarot

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.getMeaning
import com.example.simpletarot.data.withRankAndSuit
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun CardDisplay(
    drawnCard: DrawnCard,
    onReveal: () -> Unit) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { onReveal() }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                if (!drawnCard.isRevealed) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Hidden",
                        tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                        modifier = Modifier.size(48.dp)
                    )
                } else {
                    Log.d("tarot", "CardDisplay: ${drawnCard.card.name}")
                    CardImage(drawnCard)
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.small))

        if (drawnCard.isRevealed) {
            Text(text = drawnCard.getMeaning(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = spacing.extraSmall),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPreview() {
    val card = TarotCard("The Fool",
        "New beginnings, spontaneity, trust",
        "Recklessness, naivety, holding back")
        .withRankAndSuit()
    CardDisplay(DrawnCard(card, false, isRevealed = true)) { }
}
