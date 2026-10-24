package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vnteam.architecturetemplates.UIConstants.REFRESH_ANIMATION_DURATION
import com.vnteam.architecturetemplates.drawableRes
import com.vnteam.architecturetemplates.resources.DrawableResources
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.resources.LocalSmallPadding
import com.vnteam.architecturetemplates.resources.LocalStringResources
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    start = LocalSmallPadding.current.size,
                    end = LocalSmallPadding.current.size,
                    top = LocalMediumPadding.current.size,
                ),
    )
}

@Composable
fun PrimaryText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        modifier =
            Modifier
                .wrapContentSize()
                .padding(
                    start = LocalSmallPadding.current.size,
                    end = LocalSmallPadding.current.size,
                    top = LocalDefaultPadding.current.size,
                ),
    )
}

@Composable
fun SecondaryText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        modifier =
            Modifier
                .wrapContentSize()
                .padding(
                    start = LocalSmallPadding.current.size,
                    end = LocalSmallPadding.current.size,
                    top = LocalDefaultPadding.current.size,
                ),
    )
}

@Composable
fun AvatarImage(
    resId: String,
    avatarSize: Dp,
) {
    Image(
        painter = painterResource(DrawableResources.drawableRes(resId)),
        contentDescription = LocalStringResources.current.ownerAvatar,
        modifier =
            Modifier
                .wrapContentSize()
                .padding(LocalMediumPadding.current.size)
                .size(avatarSize)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun CommonTextField(
    inputValue: MutableState<TextFieldValue>,
    placeHolder: String,
    onValueChanged: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = inputValue.value,
        onValueChange = {
            inputValue.value = it
            onValueChanged.invoke(it.text)
        },
        label = { Text(text = placeHolder, color = MaterialTheme.colorScheme.onBackground) },
        shape = RoundedCornerShape(LocalMediumPadding.current.size),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color.Gray,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    start = LocalDefaultPadding.current.size,
                    top = LocalMediumPadding.current.size,
                    end = LocalDefaultPadding.current.size,
                ),
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
        keyboardActions =
            KeyboardActions(
                onNext = {
                    keyboardController?.hide()
                },
            ),
    )
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
            delay(REFRESH_ANIMATION_DURATION)
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
        modifier =
            Modifier
                .fillMaxSize()
                .padding(LocalMediumPadding.current.size),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(DrawableResources.drawableRes(DrawableResources.EMPTY_STATE)),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize(FLOAT_3)
                    .padding(LocalMediumPadding.current.size),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        )
        Text(
            text = LocalStringResources.current.emptyState,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

const val FLOAT_2 = 0.2f
const val FLOAT_3 = 0.3f
const val FLOAT_7 = 0.7f
const val FLOAT_8 = 0.8f
