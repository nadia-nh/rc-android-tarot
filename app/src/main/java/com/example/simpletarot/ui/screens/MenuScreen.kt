package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.components.CardDisplay
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    isLandscape: Boolean = false,
    dailySpread: List<DrawnCard> = listOf(),
    onCardClick: (DrawnCard) -> Unit = {}) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!dailySpread.isEmpty()) {
                Text(
                    text = "Your daily card",
                    style = MaterialTheme.typography.titleSmall
                        .copy(fontStyle = FontStyle.Italic),
                )
                Spacer(modifier = Modifier.height(spacing.small))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(dailySpread) { card ->
                        CardDisplay(
                            modifier = if (isLandscape) {
                                Modifier.fillMaxHeight(0.8f)
                            } else {
                                Modifier.height(200.dp)
                            },
                            isLandscape = isLandscape,
                            drawnCard = card,
                            onCardClick = onCardClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarotGuidanceText(
    spacing: TarotSpacing = LocalSpacing.current
) {
    Spacer(modifier = Modifier.height(spacing.large))
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Before drawing cards, focus on open-ended questions such as:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                text = "What do I need to know today?\nWhat are the themes of my work life?",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarotGuidanceTextPreview() {
    SimpleTarotTheme {
        TarotGuidanceText()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    SimpleTarotTheme {
        MenuScreen(
            dailySpread = listOf(PreviewConstants.drawnCard),
        )
    }
}