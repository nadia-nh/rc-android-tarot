package com.example.simpletarot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.withClassification

@Composable
fun CardDisplay(drawnCard: DrawnCard) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(230.dp)
            .height(300.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CardImage(drawnCard)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPreview() {
    val card = TarotCard("The Fool",
        "New beginnings, spontaneity, trust",
        "Recklessness, naivety, holding back")
        .withClassification()
    CardDisplay(DrawnCard(card, false))
}
