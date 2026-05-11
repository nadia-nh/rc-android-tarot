package com.example.simpletarot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Button
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
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
    text: String = "",
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: TarotSpacing = LocalSpacing.current,
    displayBorder: Boolean = true,
    enabled: Boolean = true,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}) {
    if (displayBorder) {
    OutlinedButton(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        StylizedButtonContents(
            isLandscape = isLandscape,
            text = text,
            style = style,
            spacing = spacing,
            displayIcon = displayIcon,
            icon = icon
        )
    }
    } else {
        Button(
            enabled = enabled,
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            StylizedButtonContents(
                isLandscape = isLandscape,
                text = text,
                style = style,
                spacing = spacing,
                displayIcon = displayIcon,
                icon = icon
            )
        }
    }
}

@Composable
fun StylizedButtonContents(
    isLandscape: Boolean = false,
    text: String = "",
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: TarotSpacing = LocalSpacing.current,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
) {
    if (isLandscape) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            StylizedButtonIconDisplay(
                text = text,
                spacing = spacing,
                displayIcon = displayIcon,
                icon = icon
            )
            Text(text = text,  style = style)
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StylizedButtonIconDisplay(
                text = text,
                spacing = spacing,
                displayIcon = displayIcon,
                icon = icon
            )
            Text(text = text, style = style)
        }
    }
}

@Composable
fun StylizedButtonIconDisplay(
    text: String = "",
    spacing: TarotSpacing = LocalSpacing.current,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
) {
    if (displayIcon && icon != null) {
        Icon(icon, contentDescription = null)

        if (text.isNotEmpty()) Spacer(Modifier.size(spacing.small))
    }
}

@Preview(showBackground = true)
@Composable
fun StylizedButtonIconDisplayPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SimpleTarotTheme {
            StylizedButtonIconDisplay(
                text = "Test Button",
                displayIcon = true,
                icon = Icons.Default.Preview
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StyledButtonPreview() {
    SimpleTarotTheme {
        StyledButton(text = "Test Button") { }
    }
}
