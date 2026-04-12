package com.example.simpletarot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.domain.model.DrawnCard
import com.example.simpletarot.domain.model.TarotCard
import com.example.simpletarot.domain.model.getResourceId
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun CardImage(
    card: DrawnCard,
    modifier: Modifier = Modifier
) {
    val tarotCard = card.card
    Image(
        painter = painterResource(id = tarotCard.getResourceId()),
        modifier = modifier
            .fillMaxHeight()
            .rotate(if (card.isReversed) 180f else 0f),
        contentScale = ContentScale.FillHeight,
        contentDescription = tarotCard.name
    )
}

@Preview
@Composable
fun CardImagePreview() {
    SimpleTarotTheme {
        CardImage(card = DrawnCard(card = TarotCard("The Fool",
            "New beginnings, character, trust",
            "Recklessness, naivety, holding back"),
            isReversed = false,
            isRevealed = true))
    }
}
