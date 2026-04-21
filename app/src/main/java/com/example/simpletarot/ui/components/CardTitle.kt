package com.example.simpletarot.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.ui.theme.PreviewConstants
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun CardTitle(
    drawnCard: DrawnCard,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    keepToOneLine: Boolean = true) {
    val name = drawnCard.card.name + if (drawnCard.isReversed) " - Reversed" else ""
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
        CardTitle(drawnCard = PreviewConstants.drawnCard)
    }
}