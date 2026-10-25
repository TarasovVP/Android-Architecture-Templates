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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.delay
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.vnteam.architecturetemplates.Res
import com.vnteam.architecturetemplates.android_architecture_template
import com.vnteam.architecturetemplates.ic_avatar_default
import com.vnteam.architecturetemplates.presentation.resources.LocalLargePadding
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalSmallPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import com.vnteam.architecturetemplates.presentation.states.InfoMessageState
import org.jetbrains.compose.resources.painterResource
import theme.Neutral400
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
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = LocalSmallPadding.current.size, end = LocalSmallPadding.current.size, top = LocalMediumPadding.current.size),
    )
}

@Composable
fun PrimaryText(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .wrapContentSize()
            .padding(start = LocalSmallPadding.current.size, end = LocalSmallPadding.current.size, top = LocalLargePadding.current.size),
    )
}

@Composable
fun SecondaryText(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .wrapContentSize()
            .padding(start = LocalSmallPadding.current.size, end = LocalSmallPadding.current.size, top = LocalLargePadding.current.size),
    )
}

@Composable
fun AvatarImage(avatarUrl: String, avatarSize: Dp) {
    SubcomposeAsyncImage(
        model = avatarUrl,
        contentDescription = LocalStringResources.current.OWNER_AVATAR,
        modifier = Modifier
            .padding(LocalSmallPadding.current.size)
            .wrapContentSize()
            .width(avatarSize)
            .height(avatarSize),
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
            is AsyncImagePainter.State.Error -> Image(painter = painterResource(Res.drawable.ic_avatar_default), contentDescription = null)
            else -> SubcomposeAsyncImageContent()
        }
    }
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
        label = { Text(text = placeHolder) },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = LocalLargePadding.current.size, top = LocalMediumPadding.current.size, end = LocalLargePadding.current.size),
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
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
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
                color = if (isEnabled) Primary500 else Neutral400,
                shape = RoundedCornerShape(LocalLargePadding.current.size)
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
        .padding(horizontal = LocalLargePadding.current.size, vertical = 8.dp)
        .fillMaxWidth()
        .border(
            1.dp,
            if (isDestructive) Color.Red else Primary400,
            shape = RoundedCornerShape(16.dp)
        )
        .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp)),
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

@Composable
fun Snackbar(
    infoMessage: MutableState<InfoMessageState?>
) {
    val showSnackbar = remember { mutableStateOf( true) }

    if (showSnackbar.value) {
        LaunchedEffect(key1 = Unit) {
            delay(2000)
            showSnackbar.value = false
            infoMessage.value = null
        }

        Snackbar(
            snackbarData = object : SnackbarData {
                override val visuals: SnackbarVisuals
                    get() = object : SnackbarVisuals {
                        override val actionLabel: String? = null
                        override val duration: SnackbarDuration = SnackbarDuration.Short
                        override val message: String = infoMessage.value?.message.orEmpty()
                        override val withDismissAction: Boolean = false
                    }

                override fun dismiss() {
                    showSnackbar.value = false
                }
                override fun performAction() = Unit
            },
            containerColor = if (infoMessage.value?.isError == true) Color.Red else Color.Green
        )
    }
}


