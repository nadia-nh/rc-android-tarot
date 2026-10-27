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
import androidx.compose.ui.unit.sp
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.ui.theme.PreviewConstants
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme

@Composable
fun CardKeywords(
    drawnCard: DrawnCard,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleSmall
        .copy(fontSize = 12.sp, lineHeight = 15.sp),
    shortVersion: Boolean = true) {
    val keywords: String = if (drawnCard.isReversed) {
        drawnCard.card.reversedKeywords
    } else {
        drawnCard.card.uprightKeywords
    }.joinToString(", ")
        .replaceFirstChar { c: Char -> c.titlecase() }

    Text(
        modifier = modifier,
        text = keywords,
        color = MaterialTheme.colorScheme.onBackground,
        style = style,
        textAlign = TextAlign.Start,
        minLines = if (shortVersion) 1 else 2,
        maxLines = if (shortVersion) 2 else 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun CardKeywordsPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardKeywords(drawnCard = PreviewConstants.drawnCard)
        }
    }
}
