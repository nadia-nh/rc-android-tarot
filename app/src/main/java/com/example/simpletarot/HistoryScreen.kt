package com.example.simpletarot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.simpletarot.ui.theme.LocalSpacing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.PreviewConstants
import com.example.simpletarot.database.ReadingWithCards
import com.example.simpletarot.database.toDrawnCard
import com.example.simpletarot.ui.theme.TarotSpacing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    isLandscape: Boolean = false,
    viewModel: TarotViewModel,
    onBack: () -> Unit = {}
){
    val history by viewModel.previousReadings.collectAsState()
    HistoryScreenStateless(history = history, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenStateless(
    history: List<ReadingWithCards>,
    onBack: () -> Unit = {}
) {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tarot History")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Tarot readings saved yet",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(spacing.medium),
                verticalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                items(history) { readingWithCards ->
                    TarotReadingItem(readingWithCards = readingWithCards)
                }
            }
        }
    }
}

@Composable
fun TarotReadingItem(
    spacing: TarotSpacing = LocalSpacing.current,
    readingWithCards: ReadingWithCards) {
    val reading = readingWithCards.reading
    val cards = readingWithCards.cards

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(spacing.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = reading.spreadType,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(reading.timestamp)),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                cards.forEach { card ->
                    CardImage(
                        card = card.toDrawnCard(),
                        modifier = Modifier.height(80.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemPreview() {
    TarotReadingItem(readingWithCards = PreviewConstants.readingWithCards)
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreenStateless(PreviewConstants.readings)
}
