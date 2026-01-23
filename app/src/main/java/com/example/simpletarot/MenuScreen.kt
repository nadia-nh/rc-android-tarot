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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun MenuScreen(onDraw: (count: Int) -> Unit) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tarot Reader", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(spacing.extraLarge))

        Button(onClick = { onDraw(1) }) {
            Text("Single Card Draw")
        }

        Button(onClick = { onDraw(3) }) {
            Text("Three Card Spread")
        }
    }
}
