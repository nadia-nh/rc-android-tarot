package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.components.CardImage
import com.example.simpletarot.ui.components.CardMeaning
import com.example.simpletarot.ui.components.CardTitle
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun CardDetailScreen(
    drawnCard: DrawnCard,
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
) {
    val spacing = LocalSpacing.current

    if (isLandscape) {
        Row(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(
                    horizontal = spacing.medium,
                    vertical = spacing.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardImage(
                card = drawnCard,
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(spacing.medium))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                CardMeaning(
                    drawnCard = drawnCard,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = spacing.large,
                    vertical = spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardImage(
                card = drawnCard,
                modifier = Modifier
                    .width(280.dp)
                    .height(420.dp)
            )

            Spacer(modifier = Modifier.height(spacing.medium))
            CardMeaning(
                drawnCard = drawnCard,
                style = MaterialTheme.typography.bodyLarge
            )
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
