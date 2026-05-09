package com.example.simpletarot.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.ui.theme.SimpleTarotTheme

@Composable
fun TarotNavigationRail(
    onHome: () -> Unit = {},
    onDraw: (count: Int) -> Unit = {},
    onOpenHistory: () -> Unit = {},
    homeSelected: Boolean = true,
    oneCardSelected: Boolean = false,
    threeCardsSelected: Boolean = false,
    historySelected: Boolean = false
){
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationRailItem(
            modifier = Modifier.weight(1f),
            selected = homeSelected,
            onClick = { onHome() },
            icon = { Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home")},
            label = { Text("Home") }
        )

        NavigationRailItem(
            modifier = Modifier.weight(1f),
            selected = oneCardSelected,
            onClick = { onDraw(1) },
            icon = { Icon(
                imageVector = Icons.Default.CropPortrait,
                contentDescription = "Draw 1 Card") },
            label = { Text("1 Card") }
        )

        NavigationRailItem(
            modifier = Modifier.weight(1f),
            selected = threeCardsSelected,
            onClick = { onDraw(3) },
            icon = { Icon(
                imageVector = Icons.Default.CropPortrait,
                contentDescription = "Draw 3 Cards") },
            label = { Text("3 Cards") }
        )

        NavigationRailItem(
            modifier = Modifier.weight(1f),
            selected = historySelected,
            onClick = { onOpenHistory() },
            icon = { Icon(
                imageVector = Icons.Default.History,
                contentDescription = "Show History") },
            label = { Text("History") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TarotNavigationRailPreview() {
    SimpleTarotTheme {
        TarotNavigationRail()
    }
}

