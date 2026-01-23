package com.example.simpletarot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpletarot.ui.theme.LocalSpacing

enum class AppScreen {
    Menu,
    Result,
}
@Composable
fun TarotMain(viewModel: TarotViewModel) {
    val spacing = LocalSpacing.current
    val currentScreen by viewModel.currentScreen.collectAsState()

    if (currentScreen == AppScreen.Menu) {
        // Menu Screen
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Tarot Reader", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(spacing.extraLarge))

            Button(onClick = {
                viewModel.drawCards(1)
            }) {
                Text("Single Card Draw")
            }

            Button(onClick = {
                viewModel.drawCards(3)
            }) {
                Text("Three Card Spread")
            }
        }
    } else if (currentScreen == AppScreen.Result) {
        ResultsScreen(viewModel) {
            viewModel.clearSpread()
        }
    }
}
