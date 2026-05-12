package com.flowworks.arcanaflux.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.domain.model.getMeaning
import com.flowworks.arcanaflux.ui.theme.PreviewConstants
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme

@Composable
fun CardMeaning(
    drawnCard: DrawnCard,
    style: TextStyle = MaterialTheme.typography.bodyLarge) {
    Text(text = drawnCard.getMeaning(),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        style = style,
        textAlign = TextAlign.Start)
}

@Preview(showBackground = true)
@Composable
fun CardMeaningPreview() {
    SimpleTarotTheme {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardMeaning(drawnCard = PreviewConstants.drawnCard)
        }
    }
}
