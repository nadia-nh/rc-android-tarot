package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.data.local.DrawnCardEntity
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.ReadingWithCards
import com.example.simpletarot.data.local.toDrawnCard
import com.example.simpletarot.ui.components.CardImage
import com.example.simpletarot.ui.components.DeleteConfirmationDialog
import com.example.simpletarot.ui.components.getColorFromSwipeState
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing
import com.example.simpletarot.ui.viewmodel.TarotViewModel
import com.example.simpletarot.util.DateUtils
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    isLandscape: Boolean = false,
    viewModel: TarotViewModel,
    onBack: () -> Unit = {}
){
    val history by viewModel.previousReadings.collectAsState()
    val pendingDeletion by viewModel.pendingDeletion.collectAsState()
    HistoryScreenStateless(
        history = history,
        onBack = onBack,
        onDeleteRequest = { viewModel.scheduleDeletion(it) })
    HandleDeleteRequest(
        reading = pendingDeletion,
        onConfirm = { viewModel.confirmDeletion() },
        onDismiss = { viewModel.cancelDeletion() })
}

@Composable
fun HistoryScreenStateless(
    history: List<ReadingWithCards>,
    onBack: () -> Unit = {},
    onDeleteRequest: (reading: ReadingEntity) -> Unit = {}
) {
    Scaffold(topBar = { HistoryScreenTopBar(onBack) })
    { padding ->
        HistoryScreenContents(
            spacing = LocalSpacing.current,
            padding = padding,
            history = history,
            onDeleteRequest = onDeleteRequest
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenTopBar(
    onBack: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = "Tarot History",
                color = MaterialTheme.colorScheme.onBackground
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
fun HistoryScreenContents(
    spacing: TarotSpacing = LocalSpacing.current,
    padding: PaddingValues = PaddingValues(),
    history: List<ReadingWithCards> = emptyList(),
    onDeleteRequest: (reading: ReadingEntity) -> Unit = {},
) {
    if (history.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(spacing.medium))
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
            items(
                items = history,
                key = { it.reading.readingId }) { readingWithCards ->
                SwipeableTarotItem(
                    item = readingWithCards,
                    onDeleteRequest = onDeleteRequest)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTarotItem(
    item: ReadingWithCards,
    onDeleteRequest: (ReadingEntity) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Disable "swipe right"
        backgroundContent = {
            val isSwiping = dismissState.currentValue != SwipeToDismissBoxValue.Settled
            val color = getColorFromSwipeState(dismissState.currentValue)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, MaterialTheme.shapes.medium)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isSwiping) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        },
        onDismiss = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                // Keep the item from disappearing until it's actually removed
                coroutineScope.launch {
                    dismissState.reset()
                    onDeleteRequest(item.reading)
                }
            }
        }
    ) {
        TarotReadingItem(readingWithCards = item)
    }
}

@Composable
fun HandleDeleteRequest(
    reading: ReadingEntity?,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}) {
    // Show Dialog if a reading is pending deletion
    reading?.let { reading ->
        DeleteConfirmationDialog(
            onConfirm = { onConfirm() },
            onDismiss = { onDismiss() }
        )
    }
}

@Composable
fun TarotReadingItem(
    spacing: TarotSpacing = LocalSpacing.current,
    readingWithCards: ReadingWithCards) {
    val reading = readingWithCards.reading

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim
        ),
    ) {
        Column(
            modifier = Modifier.padding(spacing.medium)
        ) {
            TarotReadingItemHeader(
                spreadType = reading.spreadType,
                timestamp = reading.timestamp)
            Spacer(modifier = Modifier.height(spacing.small))
            TarotReadingItemCards(
                spacing = spacing,
                cards = readingWithCards.cards)
        }
    }
}

@Composable
fun TarotReadingItemHeader(
    spreadType: String = "",
    timestamp: Long = System.currentTimeMillis()
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = spreadType,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = DateUtils.formatTimestampDate(timestamp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TarotReadingItemCards(
    spacing: TarotSpacing = LocalSpacing.current,
    cards: List<DrawnCardEntity> = emptyList()
) {
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

@Preview(showBackground = true)
@Composable
fun HistoryScreenTopBarPreview() {
    SimpleTarotTheme {
        HistoryScreenTopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenContentsPreview() {
    SimpleTarotTheme {
        HistoryScreenContents(history = PreviewConstants.readings)
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemHeaderPreview() {
    SimpleTarotTheme {
        TarotReadingItemHeader(
            spreadType = "Three Card Draw",
            timestamp = System.currentTimeMillis()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableTarotItemPreview() {
    SimpleTarotTheme {
        SwipeableTarotItem(item = PreviewConstants.readingWithCards)
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemCardsPreview() {
    SimpleTarotTheme {
        TarotReadingItemCards(
            cards = PreviewConstants.readingWithCards.cards
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemPreview() {
    SimpleTarotTheme {
        TarotReadingItem(
            readingWithCards = PreviewConstants.readingWithCards)
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    SimpleTarotTheme {
        HistoryScreenStateless(PreviewConstants.readings)
    }
}
