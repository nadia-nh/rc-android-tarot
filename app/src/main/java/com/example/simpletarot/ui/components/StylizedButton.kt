package com.example.simpletarot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletarot.ui.theme.LocalSpacing
import com.example.simpletarot.ui.theme.SimpleTarotTheme
import com.example.simpletarot.ui.theme.TarotSpacing

@Composable
fun StyledButton(
    text: String = "",
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: TarotSpacing = LocalSpacing.current,
    enabled: Boolean = true,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}) {
    OutlinedButton(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (displayIcon && icon != null) {
                Icon(icon, contentDescription = null)
                Spacer(Modifier.size(spacing.small))
            }

            Text(
                text,
                style = style
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StyledButtonPreview() {
    SimpleTarotTheme {
        StyledButton("Test Button") { }
    }
}
