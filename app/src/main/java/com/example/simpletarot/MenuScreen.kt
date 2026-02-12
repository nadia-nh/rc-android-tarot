package com.example.simpletarot

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.ui.theme.LocalSpacing

@Composable
fun MenuScreen(onDraw: (count: Int) -> Unit) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.large))
        Text("Arcana Flux Tarot",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(spacing.large))
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Focus on open-ended questions",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.height(spacing.medium))
                Text(
                    text = "What do I need to know today?\nWhat are the themes of my work life?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onDraw(1) }) {
                Text(
                    "Single Card Draw",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.large))

            Button(onClick = { onDraw(3) }) {
                Text(
                    "Three Card Spread",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen {
        Log.d("tarot", "MenuScreenPreview: $it")
    }
}