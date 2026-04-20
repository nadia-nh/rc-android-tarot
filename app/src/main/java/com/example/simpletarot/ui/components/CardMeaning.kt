package com.example.simpletarot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.domain.model.getMeaning
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun CardMeaning(drawnCard: DrawnCard) {
    Text(text = drawnCard.getMeaning(),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        maxLines = 12,
        overflow = TextOverflow.Ellipsis)
}

@Preview
@Composable
fun CardMeaningPreview() {
    SimpleTarotTheme {
        Box(modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardMeaning(drawnCard = PreviewConstants.drawnCard)
        }
    }
}
