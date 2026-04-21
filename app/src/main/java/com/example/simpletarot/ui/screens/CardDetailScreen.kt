package com.example.simpletarot.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
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
    isLandscape: Boolean = false,
    onBack: () -> Unit = {}
) {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = { CardDetailScreenTopBar(drawnCard, onBack) }
    ) { innerPadding ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(spacing.medium),
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
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(spacing.medium),
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

@Preview
@Composable
fun PreviewCardDetailScreenTopBar() {
    SimpleTarotTheme {
        CardDetailScreenTopBar(
            drawnCard = PreviewConstants.drawnCard
        )
    }
}

@Preview
@Composable
fun CardDetailScreenPreview() {
    SimpleTarotTheme {
        CardDetailScreen(
            drawnCard = PreviewConstants.drawnCard
        )
    }
}
