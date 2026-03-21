package com.example.simpletarot.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.domain.model.getMeaning
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun CardDisplay(
    isLandscape: Boolean = false,
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {}) {
    val spacing = LocalSpacing.current

    if (isLandscape) {
        Column(
            modifier = Modifier.width(160.dp).padding(spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardDisplayInternal(
                isLandscape = true,
                drawnCard = drawnCard,
                onReveal = onReveal
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth().padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CardDisplayInternal(
                isLandscape = false,
                drawnCard = drawnCard,
                onReveal = onReveal
            )
        }
    }
}

@Composable
fun CardDisplayInternal(
    isLandscape: Boolean = false,
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {}) {
    if (drawnCard.isRevealed) {
        CardWithImageOnReveal(
            isLandscape = isLandscape,
            drawnCard = drawnCard,
            onReveal = onReveal
        )
        Spacer(modifier = Modifier.size(LocalSpacing.current.small))
        CardMeaning(drawnCard = drawnCard)
    } else {
        CardWithImageOnReveal(
            isLandscape = isLandscape,
            drawnCard = drawnCard,
            onReveal = onReveal
        )
    }
}

@Composable
fun CardWithImageOnReveal(
    isLandscape: Boolean = false,
    drawnCard: DrawnCard,
    onReveal: () -> Unit = {}) {
    val modifier = Modifier.height(200.dp).clickable { onReveal() }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = if (isLandscape)
                modifier.fillMaxWidth()
            else
                modifier.width(160.dp)
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
    Text(text = drawnCard.getMeaning(),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis)
}

@Preview
@Composable
fun CardWithImageOnRevealPreview() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CardWithImageOnReveal(drawnCard = PreviewConstants.drawnCard)
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
        CardImageOrPlaceholder(
            drawnCard = PreviewConstants.drawnCard.copy(isRevealed = false))
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
        CardMeaning(drawnCard = PreviewConstants.drawnCard)
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPreview() {
    CardDisplay(drawnCard = PreviewConstants.drawnCard)
}