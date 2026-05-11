package com.example.simpletarot.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing

@Composable
fun TarotHeadline(
    spacing: TarotSpacing = LocalSpacing.current
) {
    Spacer(modifier = Modifier.height(spacing.large))
    Text("Arcana Flux Tarot",
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun TarotHeadlinePreview() {
    SimpleTarotTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TarotHeadline()
        }
    }
}
