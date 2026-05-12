package com.flowworks.arcanaflux.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.flowworks.arcanaflux.ui.theme.LocalSpacing
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme
import com.flowworks.arcanaflux.ui.theme.TarotSpacing


@Composable
fun TarotGuidanceText(
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
                text = "Before drawing cards, focus on open-ended questions such as:",
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
fun TarotGuidanceTextPreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TarotGuidanceText()
        }
    }
}
