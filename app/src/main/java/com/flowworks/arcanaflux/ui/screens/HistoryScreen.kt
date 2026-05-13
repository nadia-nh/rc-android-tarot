package com.flowworks.arcanaflux.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowworks.arcanaflux.ui.theme.PreviewConstants
import com.flowworks.arcanaflux.data.local.DrawnCardEntity
import com.flowworks.arcanaflux.data.local.ReadingEntity
import com.flowworks.arcanaflux.data.local.ReadingWithCards
import com.flowworks.arcanaflux.data.local.toDrawnCard
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.ui.components.CardImage
import com.flowworks.arcanaflux.ui.components.getColorFromSwipeState
import com.flowworks.arcanaflux.ui.theme.LocalSpacing
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme
import com.flowworks.arcanaflux.ui.theme.TarotSpacing
import com.flowworks.arcanaflux.util.DateUtils
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    history: List<ReadingWithCards> = emptyList(),
    onCardClick: (DrawnCard) -> Unit = {},
    onDeleteRequest: (reading: ReadingEntity) -> Unit = {},
    resolveCard: (DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() }
){
    when {
        history.isNotEmpty() -> FilledHistoryScreen(
            modifier = modifier,
            history = history,
            onCardClick = onCardClick,
            onDeleteRequest = onDeleteRequest,
            resolveCard = resolveCard
        )
        else -> EmptyHistoryScreen()
    }
}

@Composable
fun EmptyHistoryScreen(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme
                    .colorScheme.primary.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                "No Tarot readings saved yet",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun FilledHistoryScreen(
    modifier: Modifier = Modifier,
    history: List<ReadingWithCards> = emptyList(),
    onCardClick: (DrawnCard) -> Unit = {},
    onDeleteRequest: (reading: ReadingEntity) -> Unit = {},
    resolveCard: (DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() }
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            contentPadding = PaddingValues(spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.small),
        ) {
            items(
                items = history,
                key = { it.reading.readingId }) { readingWithCards ->
                SwipeableTarotItem(
                    item = readingWithCards,
                    onCardClick = onCardClick,
                    onDeleteRequest = onDeleteRequest,
                    resolveCard = resolveCard
                )
            }
        }
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
                color = MaterialTheme.colorScheme.onBackground,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTarotItem(
    item: ReadingWithCards,
    onCardClick: (DrawnCard) -> Unit = {},
    onDeleteRequest: (ReadingEntity) -> Unit = {},
    resolveCard: (DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() }
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
        TarotReadingItem(
            readingWithCards = item,
            onCardClick = onCardClick,
            resolveCard = resolveCard)
    }
}

@Composable
fun TarotReadingItem(
    spacing: TarotSpacing = LocalSpacing.current,
    readingWithCards: ReadingWithCards,
    onCardClick: (DrawnCard) -> Unit = {},
    resolveCard: (DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() }) {
    val reading = readingWithCards.reading

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
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
                onCardClick = onCardClick,
                cards = readingWithCards.cards,
                resolveCard = resolveCard)
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
    cards: List<DrawnCardEntity> = emptyList(),
    onCardClick: (DrawnCard) -> Unit = {},
    resolveCard: (DrawnCardEntity) -> DrawnCard = { it.toDrawnCard() }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        cards.forEach { entity ->
            val resolved = resolveCard(entity)
            CardImage(
                card = resolved,
                modifier = Modifier
                    .height(80.dp)
                    .clickable{ onCardClick(resolved) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenTopBarPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HistoryScreenTopBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemHeaderPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TarotReadingItemHeader(
                spreadType = "Three Card Draw",
                timestamp = System.currentTimeMillis()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableTarotItemPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SwipeableTarotItem(
                item = PreviewConstants.readingWithCards)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemCardsPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TarotReadingItemCards(
                cards = PreviewConstants.readingWithCards.cards
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarotReadingItemPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TarotReadingItem(
                readingWithCards = PreviewConstants.readingWithCards
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SimpleTarotTheme {
            HistoryScreen(history = PreviewConstants.readings)
        }
    }
}
