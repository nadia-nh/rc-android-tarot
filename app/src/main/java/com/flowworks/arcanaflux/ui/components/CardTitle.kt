package com.flowworks.arcanaflux.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.ui.theme.PreviewConstants
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme

@Composable
fun CardTitle(
    drawnCard: DrawnCard,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    keepToOneLine: Boolean = true) {
    val reversedText = if (keepToOneLine) " - Reversed" else " (R)"
    val name = drawnCard.card.name + if (drawnCard.isReversed) reversedText else ""
    Text(
        text = name,
        color = MaterialTheme.colorScheme.onBackground,
        style = style,
        textAlign = TextAlign.Center,
        minLines = if (keepToOneLine) 1 else 2,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardTitle(drawnCard = PreviewConstants.drawnCard)
        }
    }
}