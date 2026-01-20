package com.example.simpletarot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.getResourceId

@Composable
fun CardImage(card: DrawnCard) {
    val tarotCard = card.card
    Image(
        painter = painterResource(id = tarotCard.getResourceId()),
        modifier = Modifier
            .width(208.dp)
            .height(299.dp)
            .rotate(if (card.isReversed) 180f else 0f),
        contentDescription = tarotCard.name
    )
}

@Preview
@Composable
fun CardImagePreview() {
    CardImage(card = DrawnCard(card = TarotCard("The Fool",
        "New beginnings, character, trust",
        "Recklessness, naivety, holding back"),
        isReversed = false,
        isRevealed = true))
}
