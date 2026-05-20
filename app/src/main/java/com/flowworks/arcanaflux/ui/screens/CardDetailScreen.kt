package com.flowworks.arcanaflux.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.ui.components.CardImage
import com.flowworks.arcanaflux.ui.components.CardMeaning
import com.flowworks.arcanaflux.ui.components.CardTitle
import com.flowworks.arcanaflux.ui.theme.LocalSpacing
import com.flowworks.arcanaflux.ui.theme.PreviewConstants
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme
import com.flowworks.arcanaflux.ui.theme.TarotSpacing

@Composable
fun CardDetailScreen(
    drawnCard: DrawnCard,
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
) {
    val spacing = LocalSpacing.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CardDetailScreenContents(
            drawnCard = drawnCard,
            modifier = if (isLandscape) {
                Modifier.padding(
                    horizontal = spacing.medium,
                    vertical = spacing.large)
            } else {
                Modifier.padding(
                    horizontal = spacing.large,
                    vertical = spacing.medium)
            },
            cardModifier = if (isLandscape) {
                Modifier.width(480.dp).fillMaxHeight()
            } else {
                Modifier.width(480.dp).height(420.dp)
            },
            isLandscape = isLandscape,
        )
    }
}

@Composable
fun CardDetailScreenContents(
    drawnCard: DrawnCard,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    spacing: TarotSpacing = LocalSpacing.current,
    isLandscape: Boolean = false,
) {
    val scrollState = rememberScrollState()

    if (isLandscape) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardImage(card = drawnCard, modifier = cardModifier)
            Spacer(modifier = Modifier.width(spacing.medium))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(state = scrollState)
            ) {
                CardMeaning(drawnCard = drawnCard)
            }
        }
    } else {
        Column(
            modifier = modifier.verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardImage(card = drawnCard, modifier = cardModifier)
            Spacer(modifier = Modifier.height(spacing.medium))
            CardMeaning(drawnCard = drawnCard)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreenTopBar(
    drawnCard: DrawnCard,
    onBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            CardTitle(
                drawnCard = drawnCard,
                style = MaterialTheme.typography.titleMedium
                    .copy(fontWeight = FontWeight.Bold))
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }},
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCardDetailScreenTopBar() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardDetailScreenTopBar(
                drawnCard = PreviewConstants.drawnCard
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDetailScreenPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardDetailScreen(
                drawnCard = PreviewConstants.drawnCard
            )
        }
    }
}
