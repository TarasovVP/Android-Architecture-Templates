package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vnteam.architecturetemplates.UIConstants
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.resources.LocalLargePadding
import com.vnteam.architecturetemplates.resources.LocalLargerPadding
import com.vnteam.architecturetemplates.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.theme.Primary500

@Composable
fun ConfirmationDialog(
    title: String,
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onConfirmationClick: () -> Unit,
) {
    if (!showDialog.value) return

    Dialog(onDismissRequest = onDismiss) {
        val shape = RoundedCornerShape(LocalMediumPadding.current.size)
        Surface(
            shape = shape,
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, Primary500),
            tonalElevation = LocalMediumPadding.current.size,
            modifier =
                Modifier
                    .widthIn(
                        min = UIConstants.DIALOG_MIN_WIDTH.dp,
                        max = UIConstants.DIALOG_MAX_WIDTH.dp,
                    ).heightIn(min = UIConstants.DIALOG_MAX_HEIGHT.dp)
                    .clip(shape),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LocalDefaultPadding.current.size,
                            vertical = LocalDefaultPadding.current.size,
                        ),
                verticalArrangement = Arrangement.spacedBy(LocalDefaultPadding.current.size),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                SubmitButtons(
                    isEnabled = true,
                    onDismiss = onDismiss,
                    onConfirmationClick = onConfirmationClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseChangeAvatarDialog(
    avatarList: List<String>,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = { onDismiss() },
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Text(
            text = LocalStringResources.current.changeAvatar,
            style = MaterialTheme.typography.bodyLarge,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(LocalMediumPadding.current.size),
            textAlign = TextAlign.Center,
        )
        LazyVerticalGrid(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = LocalMediumPadding.current.size,
                        end = LocalMediumPadding.current.size,
                        bottom = LocalLargerPadding.current.size,
                    ),
            columns = GridCells.Adaptive(minSize = LocalLargeAvatarSize.current.size + LocalLargePadding.current.size),
        ) {
            items(avatarList) { avatar ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClick(avatar)
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    AvatarImage(avatar, LocalLargeAvatarSize.current.size)
                }
            }
        }
    }
}
