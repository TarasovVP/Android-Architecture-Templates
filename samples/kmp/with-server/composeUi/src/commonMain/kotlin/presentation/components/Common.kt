package presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vnteam.architecturetemplates.Res
import com.vnteam.architecturetemplates.android_architecture_template
import com.vnteam.architecturetemplates.presentation.resources.DrawableResources
import com.vnteam.architecturetemplates.presentation.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.presentation.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalSmallPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import presentation.drawableRes
import theme.Neutral700
import theme.Primary400
import theme.Primary500

@Composable
fun SplashScreen() {
    val screenWidth = remember { mutableStateOf(0.dp) }
    val minSize = screenWidth.value * 0.2f
    val maxSize = screenWidth.value * 0.7f

    val infiniteTransition = rememberInfiniteTransition()
    val size = infiniteTransition.animateFloat(
        initialValue = minSize.value,
        targetValue = maxSize.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                with(localDensity) {
                    screenWidth.value = size.width.toDp()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.android_architecture_template),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size.value.dp).clip(RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun HeaderText(
    text: String,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = LocalSmallPadding.current.size,
                end = LocalSmallPadding.current.size,
                top = LocalMediumPadding.current.size
            ),
    )
}

@Composable
fun PrimaryText(
    text: String,
) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .wrapContentSize()
            .padding(
                start = LocalSmallPadding.current.size,
                end = LocalSmallPadding.current.size,
                top = LocalDefaultPadding.current.size
            ),
    )
}

@Composable
fun SecondaryText(
    text: String,
) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .wrapContentSize()
            .padding(
                start = LocalSmallPadding.current.size,
                end = LocalSmallPadding.current.size,
                top = LocalDefaultPadding.current.size
            ),
    )
}

@Composable
fun AvatarImage(resId: String, avatarSize: Dp) {
    Image(
        painter = painterResource(DrawableResources.drawableRes(resId)),
        contentDescription = LocalStringResources.current.OWNER_AVATAR,
        modifier = Modifier
            .wrapContentSize()
            .padding(LocalMediumPadding.current.size)
            .size(avatarSize)
            .clip(CircleShape)
            .border(1.dp, Color.Gray, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CommonTextField(
    inputValue: MutableState<TextFieldValue>,
    placeHolder: String,
    onValueChanged: (String) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = inputValue.value,
        onValueChange = {
            inputValue.value = it
            onValueChanged.invoke(it.text)
        },
        label = { Text(text = placeHolder, color = MaterialTheme.colorScheme.onBackground) },
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = LocalDefaultPadding.current.size, top = LocalMediumPadding.current.size, end = LocalDefaultPadding.current.size),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                keyboardController?.hide()
            }
        )
    )
}

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
                        modifier = Modifier
                            .wrapContentSize()
                            .border(1.dp, Primary500, shape = RoundedCornerShape(16.dp))
                            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                        )
                        SubmitButtons(true, onDismiss, onConfirmationClick)
                    }
                }
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    isEnabled: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(enabled = isEnabled,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(
                color = if (isEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(LocalDefaultPadding.current.size)
            )
            .testTag("sign_up_button"),
        onClick = {
            onClick.invoke()
        }
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun SecondaryButton(text: String, isDestructive: Boolean, modifier: Modifier, onClick: () -> Unit) {
    TextButton(modifier = modifier
        .padding(horizontal = LocalDefaultPadding.current.size, vertical = 8.dp)
        .fillMaxWidth()
        .border(
            1.dp,
            if (isDestructive) Color.Red else Primary400,
            shape = RoundedCornerShape(16.dp)
        )
        .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
        onClick = {
            onClick.invoke()
        }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SecondaryButton(text = LocalStringResources.current.BUTTON_CANCEL, false, Modifier.weight(1f), onClick = onDismiss)
        PrimaryButton(text = LocalStringResources.current.BUTTON_OK, isEnabled, Modifier.weight(1f)) {
            onConfirmationClick.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshableLazyList(
    isEmpty: Boolean = false,
    content: LazyListScope.() -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
            delay(1500)
            state.endRefresh()
        }
    }
    if (isEmpty && !state.isRefreshing) {
        EmptyState()
    }
    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
        LazyColumn(Modifier.fillMaxSize().padding(LocalMediumPadding.current.size)) {
            content()
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalMediumPadding.current.size),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(DrawableResources.drawableRes(DrawableResources.EMPTY_STATE)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.3f)
                .padding(LocalMediumPadding.current.size),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
        Text(
            text = LocalStringResources.current.EMPTY_STATE,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAvatarDialog(avatarList: List<String>, onDismiss: () -> Unit, onClick: (String) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = { onDismiss() },
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Text(
            text = LocalStringResources.current.CHANGE_AVATAR,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalMediumPadding.current.size),
            textAlign = TextAlign.Center
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = LocalMediumPadding.current.size,
                    end = LocalMediumPadding.current.size,
                    bottom = LocalDefaultPadding.current.size * 3
                ),
            columns = GridCells.Adaptive(minSize = LocalLargeAvatarSize.current.size + LocalDefaultPadding.current.size * 2)
        ) {
            items(avatarList) { avatar ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(avatar)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    AvatarImage(avatar, LocalLargeAvatarSize.current.size)
                }
            }
        }
    }
}

