package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    if (showDialog.value) {
        Column {
            Dialog(
                onDismissRequest = onDismiss,
                content = {
                    Column(
                        modifier =
                            Modifier
                                .wrapContentSize()
                                .border(1.dp, Primary500, shape = RoundedCornerShape(LocalLargeAvatarSize.current.size))
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(LocalLargeAvatarSize.current.size),
                                ),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(LocalLargeAvatarSize.current.size),
                            textAlign = TextAlign.Center,
                        )
                        SubmitButtons(true, onDismiss, onConfirmationClick)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAvatarDialog(
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
