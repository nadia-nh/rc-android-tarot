package com.example.simpletarot

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun MenuScreen(
    isLandscape: Boolean = false,
    onDraw: (count: Int) -> Unit = {},
    onOpenHistory: () -> Unit = {}) {
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
                    text = "What do I need to know today?\nWhat are the themes of my work / love / career life?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        if (isLandscape) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StyledButton("Single Card Draw") { onDraw(1) }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton("Three Card Spread") { onDraw(3) }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton("Show History") { onOpenHistory() }
            }
        } else {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StyledButton("Single Card Draw") { onDraw(1) }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton("Three Card Spread") { onDraw(3) }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton("Show History") { onOpenHistory() }
            }
        }
    }
}

@Composable
fun StyledButton(
    text: String = "Single Card Draw",
    onClick: () -> Unit = {}) {
    Button(onClick = onClick) {
        Text(
            text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StyledButtonPreview() {
    StyledButton("Test Button") { }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen(onDraw = {
        Log.d("tarot", "MenuScreenPreview: $it")
    })
}