package com.example.simpletarot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.simpletarot.database.ReadingWithCards

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
fun TarotReadingItem(readingWithCards: ReadingWithCards) {
    Text(readingWithCards.reading.spreadType)
}

