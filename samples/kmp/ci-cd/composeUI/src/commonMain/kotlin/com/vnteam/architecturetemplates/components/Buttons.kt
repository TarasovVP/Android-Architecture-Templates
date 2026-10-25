package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vnteam.architecturetemplates.UIConstants
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalLargerPadding
import com.vnteam.architecturetemplates.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.resources.Res
import com.vnteam.architecturetemplates.resources.getThemeSwitchDescription
import com.vnteam.architecturetemplates.resources.ic_dark_mode
import com.vnteam.architecturetemplates.resources.ic_light_mode
import org.jetbrains.compose.resources.painterResource

@Composable
fun SubmitButtons(
    isEnabled: Boolean = true,
    onDismiss: () -> Unit,
    onConfirmationClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(LocalDefaultPadding.current.size),
        horizontalArrangement =
            Arrangement.spacedBy(LocalDefaultPadding.current.size),
    ) {
        SecondaryButton(
            text = LocalStringResources.current.buttonCancel,
            modifier = Modifier.weight(1f),
            onClick = onDismiss,
        )
        PrimaryButton(
            text = LocalStringResources.current.buttonOk,
            isEnabled = isEnabled,
            modifier = Modifier.weight(1f),
            onClick = onConfirmationClick,
        )
    }
}

@Composable
fun PrimaryButton(
    text: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier.heightIn(min = LocalLargerPadding.current.size),
        shape = RoundedCornerShape(LocalMediumPadding.current.size),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        contentPadding =
            PaddingValues(
                horizontal = LocalDefaultPadding.current.size,
                vertical = LocalDefaultPadding.current.size,
            ),
    ) {
        Text(text = text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    isDestructive: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val tint =
        if (isDestructive) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        }

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = LocalLargerPadding.current.size),
        shape = RoundedCornerShape(LocalMediumPadding.current.size),
        border = BorderStroke(1.dp, tint),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = tint,
                disabledContentColor = tint.copy(alpha = UIConstants.FLOAT_3),
            ),
        contentPadding =
            PaddingValues(
                horizontal = LocalDefaultPadding.current.size,
                vertical = LocalDefaultPadding.current.size,
            ),
    ) {
        Text(text = text, color = if (enabled) tint else tint.copy(alpha = UIConstants.FLOAT_3))
    }
}

@Composable
fun LanguageSwitcherButton(appVm: AppViewModel) {
    IconButton(onClick = { appVm.setLanguage(appVm.getNewLanguage()) }) {
        Text(appVm.getNewLanguage(), color = Color.White)
    }
}

@Composable
fun ThemeToggleButton(appVm: AppViewModel) {
    IconButton(onClick = { appVm.setIsDarkTheme(appVm.isDarkTheme.value != true) }) {
        Icon(
            painter =
                painterResource(
                    if (appVm.isDarkTheme.value == true) {
                        Res.drawable.ic_light_mode
                    } else {
                        Res.drawable.ic_dark_mode
                    },
                ),
            contentDescription = getThemeSwitchDescription(appVm.isDarkTheme.value == true),
            tint = Color.White,
        )
    }
}
