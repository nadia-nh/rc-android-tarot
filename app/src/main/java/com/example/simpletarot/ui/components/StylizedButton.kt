package com.example.simpletarot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StyledButton(
    text: String = "",
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit = {}) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text,
            style = style
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StyledButtonPreview() {
    StyledButton("Test Button") { }
}
