package com.example.simpletarot.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.ui.components.StyledButton
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing

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
        MenuScreenHeadline(spacing)
        MenuScreenGuidanceText(spacing)

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StyledButton(
                text = "1 Card",
                style = MaterialTheme.typography.labelSmall,
                displayIcon = true,
                icon = Icons.Default.CropPortrait) {
                onDraw(1)
                }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton(
                    text = "3 Cards",
                    style = MaterialTheme.typography.labelSmall,
                    displayIcon = true,
                    icon = Icons.Default.CropPortrait) {
                    onDraw(3)
                }
                Spacer(modifier = Modifier.size(spacing.large))
                StyledButton(
                    text = "History",
                    style = MaterialTheme.typography.labelSmall,
                    displayIcon = true,
                    icon = Icons.Default.History) {
                    onOpenHistory()
                }
            }
    }
}

@Composable
fun MenuScreenHeadline(
    spacing: TarotSpacing = LocalSpacing.current
) {
    Spacer(modifier = Modifier.height(spacing.large))
    Text("Arcana Flux Tarot",
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall)
}

@Composable
fun MenuScreenGuidanceText(
    spacing: TarotSpacing = LocalSpacing.current
) {
    Spacer(modifier = Modifier.height(spacing.large))
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Focus on open-ended questions such as:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                text = "What do I need to know today?\nWhat are the themes of my work life?",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenHeadlinePreview() {
    SimpleTarotTheme {
        MenuScreenHeadline()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenGuidanceTextPreview() {
    SimpleTarotTheme {
        MenuScreenGuidanceText()
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    SimpleTarotTheme {
        MenuScreen(onDraw = {
            Log.d("tarot", "MenuScreenPreview: $it")
        })
    }
}