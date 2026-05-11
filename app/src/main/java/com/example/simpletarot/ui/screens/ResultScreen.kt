package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.ui.components.CardDisplay
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.components.StyledButton
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.TarotSpacing
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun ResultsScreen(
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
    cards : List<DrawnCard>,
    isSaved: Boolean = false,
    allCardsRevealed: Boolean = false,
    onSave: () -> Unit = {},
    onReveal: (index: Int) -> Unit = {},
    onCardClick: (DrawnCard) -> Unit = {}) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CardsDisplay(
                isLandscape = isLandscape,
                spacing = spacing,
                cards = cards,
                onReveal = onReveal,
                onCardClick = onCardClick)
        }

        SaveButton(
            isLandscape = isLandscape,
            spacing = spacing,
            isSaved,
            allCardsRevealed,
            onSave)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreenTopAppBar(
    cardCount: Int = 1,
    onBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = if (cardCount == 1) "Your Card" else "Your Spread",
                color = MaterialTheme . colorScheme . onBackground,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back")
            }
        }
    )
}

@Composable
fun CardsDisplay(
    isLandscape: Boolean = false,
    spacing: TarotSpacing = LocalSpacing.current,
    cards: List<DrawnCard>,
    onReveal: (index: Int) -> Unit = {},
    onCardClick: (DrawnCard) -> Unit = {}) {
    if (isLandscape) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(horizontal = spacing.extraLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(cards) { index, card ->
                CardDisplay(
                    modifier = Modifier.height(200.dp),
                    isLandscape = isLandscape,
                    drawnCard = card,
                    onReveal = { onReveal(index) },
                    onCardClick = onCardClick
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(vertical = spacing.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(cards) { index, card ->
                CardDisplay(
                    modifier = Modifier.height(200.dp),
                    isLandscape = isLandscape,
                    drawnCard = card,
                    onReveal = { onReveal(index) },
                    onCardClick = onCardClick
                )
            }
        }
    }
}

@Composable
fun SaveButton(
    isLandscape: Boolean = false,
    spacing: TarotSpacing = LocalSpacing.current,
    isSaved: Boolean,
    allCardsRevealed: Boolean,
    onSave: () -> Unit = {}) {
    StyledButton(
        isLandscape = isLandscape,
        text = if (isSaved) "Saved" else "Save Reading",
        style = MaterialTheme.typography.bodyMedium,
        spacing = spacing,
        enabled = allCardsRevealed && !isSaved,
        displayIcon = isSaved,
        icon = Icons.Default.Check,
        onClick = onSave
    )
}

@Preview(showBackground = true)
@Composable
fun CardsDisplayPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardsDisplay(cards = PreviewConstants.tarotCards)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaveButtonPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SaveButton(isSaved = false, allCardsRevealed = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ResultsScreen(cards = PreviewConstants.tarotCards)
        }
    }
}