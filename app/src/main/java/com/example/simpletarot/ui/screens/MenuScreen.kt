package com.example.simpletarot.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.components.CardDisplay
import com.example.simpletarot.ui.components.StyledButton
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    isLandscape: Boolean = false,
    dailySpread: List<DrawnCard> = listOf(),
    onDraw: (count: Int) -> Unit = {},
    onOpenHistory: () -> Unit = {},
    onCardClick: (DrawnCard) -> Unit = {}) {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {MenuScreenHeadline(spacing)}
            )
        },
        bottomBar = {
            MenuScreenButtons(
                spacing = spacing,
                isLandscape = isLandscape,
                onDraw = onDraw,
                onOpenHistory = onOpenHistory
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(innerPadding),
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

            MenuScreenGuidanceText(spacing)
        }
    }
}

@Composable
fun MenuScreenHeadline(
    spacing: TarotSpacing = LocalSpacing.current
) {
    Spacer(modifier = Modifier.height(spacing.large))
    Text("Arcana Flux Tarot",
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
        )
}

@Composable
fun MenuScreenGuidanceText(
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

@Composable
fun MenuScreenButtons(
    spacing: TarotSpacing = LocalSpacing.current,
    isLandscape: Boolean = false,
    onDraw: (count: Int) -> Unit = {},
    onOpenHistory: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(
                start = if (isLandscape) spacing.large else spacing.medium,
                end = if (isLandscape) spacing.large else spacing.medium,
                top = if (isLandscape) spacing.medium else spacing.large,
                bottom = spacing.large),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = MaterialTheme.typography.labelSmall
            .copy(fontSize = if (isLandscape) 12.sp else 9.sp)
        StyledButton(
            modifier = Modifier.weight(1f),
            isLandscape = isLandscape,
            text = "1 Card",
            style = textStyle,
            displayIcon = true,
            icon = Icons.Default.CropPortrait) {
            onDraw(1)
        }
        Spacer(modifier = Modifier.size(spacing.large))
        StyledButton(
            modifier = Modifier.weight(1f),
            isLandscape = isLandscape,
            text = "3 Cards",
            style = textStyle,
            displayIcon = true,
            icon = Icons.Default.CropPortrait) {
            onDraw(3)
        }
        Spacer(modifier = Modifier.size(spacing.large))
        StyledButton(
            modifier = Modifier.weight(1f),
            isLandscape = isLandscape,
            text = "History",
            style = textStyle,
            displayIcon = true,
            icon = Icons.Default.History) {
            onOpenHistory()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenHeadlinePreview() {
    SimpleTarotTheme {
        MenuScreenHeadline()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenGuidanceTextPreview() {
    SimpleTarotTheme {
        MenuScreenGuidanceText()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenButtonsPreview() {
    SimpleTarotTheme {
        MenuScreenButtons()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    SimpleTarotTheme {
        MenuScreen(
            dailySpread = listOf(PreviewConstants.drawnCard),
            onDraw = {
                Log.d("tarot", "MenuScreenPreview: $it")
            }
        )
    }
}