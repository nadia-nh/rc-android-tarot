package com.example.simpletarot.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun CardDisplay(
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {},
    onCardClick: (DrawnCard) -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardWithImageOnReveal(
            modifier = modifier,
            drawnCard = drawnCard,
            onReveal = onReveal,
            onCardClick = onCardClick
        )
        if (drawnCard.isRevealed) {
            Spacer(modifier = Modifier.size(LocalSpacing.current.small))
            CardTitle(
                drawnCard = drawnCard,
                keepToOneLine = !isLandscape)
        }
    }
}

@Composable
fun CardWithImageOnReveal(
    modifier: Modifier = Modifier,
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {},
    onCardClick: (DrawnCard) -> Unit = {}) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.aspectRatio(0.8f)
            .clickable {
                    if (drawnCard.isRevealed) onCardClick(drawnCard) else onReveal()
                }
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
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.size(48.dp)
            )
        } else {
            Log.d("tarot", "CardDisplay: ${drawnCard.card.name}")
            CardImage(drawnCard)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardWithImageOnRevealPreview() {
    SimpleTarotTheme {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardWithImageOnReveal(
                modifier = Modifier.height(200.dp),
                drawnCard = PreviewConstants.drawnCard)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardImageOrPlaceholderPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardImageOrPlaceholder(
                drawnCard = PreviewConstants.drawnCard.copy(isRevealed = false)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardDisplay(
                modifier = Modifier.height(200.dp),
                drawnCard = PreviewConstants.drawnCard
            )
        }
    }
}