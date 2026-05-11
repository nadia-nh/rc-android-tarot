package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
    dailySpread: List<DrawnCard> = listOf(),
    onCardClick: (DrawnCard) -> Unit = {}) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MenuScreenDailySpread(
            spacing = LocalSpacing.current,
            isLandscape = isLandscape,
            dailySpread = dailySpread,
            onCardClick = onCardClick
        )
    }
}

@Composable
fun MenuScreenDailySpread(
    spacing: TarotSpacing = LocalSpacing.current,
    isLandscape: Boolean = false,
    dailySpread: List<DrawnCard> = listOf(),
    onCardClick: (DrawnCard) -> Unit = {}
) {
    Column(
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

@Preview(showBackground = true)
@Composable
fun MenuScreenDailySpreadPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
        MenuScreenDailySpread(
            dailySpread = listOf(PreviewConstants.drawnCard),
        )
            }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MenuScreen(
                dailySpread = listOf(PreviewConstants.drawnCard),
            )
        }
    }
}