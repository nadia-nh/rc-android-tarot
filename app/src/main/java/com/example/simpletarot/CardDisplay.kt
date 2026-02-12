package com.example.simpletarot

import android.util.Log
import androidx.compose.foundation.background
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
import com.example.simpletarot.PreviewConstants.drawnCard
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.getMeaning
import com.example.simpletarot.data.withRankAndSuit
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun CardDisplay(
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {}) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardWithImageOnReveal(drawnCard = drawnCard, onReveal = onReveal)
        Spacer(modifier = Modifier.height(spacing.small))
        CardMeaning(drawnCard = drawnCard)
    }
}

@Composable
fun CardWithImageOnReveal(
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {}) {
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
    ) { CardImageOrPlaceholder(drawnCard) }
}

@Composable
fun CardImageOrPlaceholder(drawnCard: DrawnCard) {
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
@Composable
fun CardMeaning(drawnCard: DrawnCard) {
    if (drawnCard.isRevealed) {
        Text(text = drawnCard.getMeaning(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis)
    }
}

@Preview
@Composable
fun CardWithImageOnRevealPreview() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CardWithImageOnReveal(drawnCard = drawnCard)
    }
}

@Preview
@Composable
fun CardImageOrPlaceholderPreview() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CardImageOrPlaceholder(drawnCard = drawnCard.copy(isRevealed = false))
    }
}

@Preview
@Composable
fun CardMeaningPreview() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CardMeaning(drawnCard = drawnCard)
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPreview() {
    CardDisplay(drawnCard = drawnCard)
}

private object PreviewConstants {
    val tarotCard = TarotCard("The Fool",
        "New beginnings, spontaneity, trust",
        "Recklessness, naivety, holding back")
        .withRankAndSuit()
    val drawnCard = DrawnCard(tarotCard, false, isRevealed = true)
}