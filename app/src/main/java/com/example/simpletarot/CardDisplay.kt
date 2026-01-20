package com.example.simpletarot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotCard
import com.example.simpletarot.data.withClassification

@Composable
fun CardDisplay(drawnCard: DrawnCard) {
    val card = drawnCard.card

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.width(230.dp).height(300.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = card.suit.displayName,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = card.name + if (drawnCard.isReversed) " Reversed" else "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = if (drawnCard.isReversed) card.reversedMeaning else card.uprightMeaning,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
            )
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
