package com.flowworks.arcanaflux.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.flowworks.arcanaflux.ui.theme.LocalSpacing
import com.flowworks.arcanaflux.ui.theme.SimpleTarotTheme
import com.flowworks.arcanaflux.ui.theme.TarotSpacing

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
            forceHorizontalLayout = true,
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
                forceHorizontalLayout = true,
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
    forceHorizontalLayout: Boolean = false,
    text: String = "",
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: TarotSpacing = LocalSpacing.current,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
) {
    val spacingSize = if (text.isNotEmpty()) {
        spacing.small
    } else {
        0.dp
    }

    if (isLandscape || forceHorizontalLayout) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            StylizedButtonIconDisplay(
                modifier = Modifier.width(spacingSize),
                displayIcon = displayIcon,
                icon = icon
            )
            Text(text = text,  style = style)
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StylizedButtonIconDisplay(
                modifier = Modifier.height(spacingSize),
                displayIcon = displayIcon,
                icon = icon
            )
            Text(text = text, style = style)
        }
    }
}

@Composable
private fun StylizedButtonIconDisplay(
    modifier: Modifier = Modifier,
    displayIcon: Boolean = false,
    icon: ImageVector? = null,
) {
    if (displayIcon && icon != null) {
        Icon(icon, contentDescription = null)
        Spacer(modifier)
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
                displayIcon = true,
                icon = Icons.Default.Preview
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StyledButtonPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SimpleTarotTheme {
            StyledButton(text = "Test Button") { }
        }
    }
}
