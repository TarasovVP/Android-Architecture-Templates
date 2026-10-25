package com.vnteam.architecturetemplates.presentation.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vnteam.architecturetemplates.presentation.NavigationScreens
import com.vnteam.architecturetemplates.presentation.components.AvatarImage
import com.vnteam.architecturetemplates.presentation.components.ConfirmationDialog
import com.vnteam.architecturetemplates.presentation.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumAvatarSize
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalSmallAvatarSize
import com.vnteam.architecturetemplates.presentation.resources.LocalSmallPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import kotlinx.browser.window
import navigateTo

@Composable
fun ListContent(
    viewState: ListViewState,
    screenState: MutableState<ScreenState>,
    onItemClick: (DemoObjectUI, String) -> Unit
) {
    ListScreenStateContent(screenState)
    Box {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(LocalDefaultPadding.current.size)) {
            items(viewState.demoObjectUIs.orEmpty()) { item ->
                DemoObjectItem(item, onItemClick)
            }
        }
        ConfirmationDialog(
            showDialog = viewState.isConfirmationDialogVisible,
            title = LocalStringResources.current.DELETE,
            onConfirmationClick = { onItemClick(DemoObjectUI(demoObjectId = viewState.demoObjectToDelete), "delete") },
            onDismiss = { viewState.isConfirmationDialogVisible.value = false })
    }
}

@Composable
fun ListScreenStateContent(screenState: MutableState<ScreenState>) {
    screenState.value = screenState.value.copy(
        appBarState = screenState.value.appBarState.copy(
            appBarTitle = LocalStringResources.current.APP_NAME
        ),
        floatingActionState = screenState.value.floatingActionState.copy(
            floatingActionButtonVisible = true,
            floatingActionButtonTitle = LocalStringResources.current.ADD,
            floatingActionButtonAction = {
                window.navigateTo(NavigationScreens.CreateScreen.route)
            }
        )
    )
}

@Composable
fun DemoObjectItem(item: DemoObjectUI, onItemClick: (DemoObjectUI, String) -> Unit) {
    Card(modifier = Modifier.padding(LocalMediumPadding.current.size).fillMaxSize().clickable { onItemClick(item, "details") }) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(LocalSmallPadding.current.size)) {
            AvatarImage(resId = item.owner?.avatarUrl.orEmpty(), avatarSize = LocalMediumAvatarSize.current.size)
            Text(text = item.name.orEmpty(), modifier = Modifier
                .padding(LocalMediumPadding.current.size).weight(1f))
            IconButton(onClick = { onItemClick(item, "confirm_delete") }) {
                Icon( modifier = Modifier
                    .size(LocalSmallAvatarSize.current.size),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = LocalStringResources.current.DELETE,
                )
            }
        }
    }
}