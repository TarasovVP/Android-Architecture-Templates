package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.resources.Res
import com.vnteam.architecturetemplates.resources.getThemeSwitchDescription
import com.vnteam.architecturetemplates.resources.ic_dark_mode
import com.vnteam.architecturetemplates.resources.ic_light_mode
import com.vnteam.architecturetemplates.theme.Neutral700
import com.vnteam.architecturetemplates.theme.Primary400
import org.jetbrains.compose.resources.painterResource

@Composable
fun PrimaryButton(
    text: String,
    isEnabled: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        enabled = isEnabled,
        modifier =
            modifier
                .padding(
                    horizontal = LocalLargeAvatarSize.current.size,
                    vertical = LocalMediumPadding.current.size,
                )
                .fillMaxWidth()
                .background(
                    color =
                        if (isEnabled) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                    shape = RoundedCornerShape(LocalDefaultPadding.current.size),
                ),
        onClick = {
            onClick.invoke()
        },
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    isDestructive: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        modifier =
            modifier
                .padding(
                    horizontal = LocalDefaultPadding.current.size,
                    vertical = LocalMediumPadding.current.size,
                )
                .fillMaxWidth()
                .border(
                    1.dp,
                    if (isDestructive) {
                        Color.Red
                    } else {
                        Primary400
                    },
                    shape = RoundedCornerShape(LocalLargeAvatarSize.current.size),
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(LocalLargeAvatarSize.current.size),
                ),
        onClick = {
            onClick.invoke()
        },
    ) {
        Text(text = text, color = if (isDestructive) Color.Red else Neutral700)
    }
}

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
                .padding(LocalMediumPadding.current.size),
        horizontalArrangement = Arrangement.Center,
    ) {
        SecondaryButton(
            text = LocalStringResources.current.buttonCancel,
            false,
            Modifier.weight(1f),
            onClick = onDismiss,
        )
        PrimaryButton(
            text = LocalStringResources.current.buttonOk,
            isEnabled,
            Modifier.weight(1f),
        ) {
            onConfirmationClick.invoke()
        }
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
