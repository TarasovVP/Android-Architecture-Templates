package presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import com.vnteam.architecturetemplates.presentation.resources.LocalLargePadding
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumAvatarSize
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalSmallPadding
import org.koin.compose.koinInject
import presentation.ScreenState
import presentation.components.AvatarImage

@Composable
fun ListScreen(screenState: MutableState<ScreenState>, onItemClick: (DemoObjectUI) -> Unit) {
    val listViewModel = koinInject<ListViewModel>()
    val viewModel = androidx.lifecycle.viewmodel.compose.viewModel { listViewModel }
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewState.value.takeIf { it.demoObject == null }?.let {
            viewModel.processIntent(ListIntent.ClearDemoObjects())
            viewModel.processIntent(ListIntent.LoadDemoObjects())
        }
    }

    LaunchedEffect(viewState.value.infoMessage.value) {
        viewState.value.infoMessage.value.takeIf { it != null }?.let {
            screenState.value = screenState.value.copy(snackbarVisible = true, snackbarMessage = it.message, isSnackbarError = it.isError)
            viewState.value.infoMessage.value = null
        }
    }
    LaunchedEffect(viewState.value.isLoading) {
        screenState.value = screenState.value.copy(isProgressVisible = viewState.value.isLoading)
    }
    ListContent(viewState.value, onItemClick)
}

@Composable
fun ListContent(viewState: ListViewState, onItemClick: (DemoObjectUI) -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalLargePadding.current.size),
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(viewState.demoObject.orEmpty()) { item ->
                    DemoObjectItem(item, onItemClick)
                }
            }
        }
    }
}

@Composable
fun DemoObjectItem(item: DemoObjectUI, onItemClick: (DemoObjectUI) -> Unit) {
    Card(modifier = Modifier.padding(LocalMediumPadding.current.size).fillMaxSize().clickable { onItemClick(item) }) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(LocalSmallPadding.current.size)) {
            AvatarImage(avatarUrl = item.owner?.avatarUrl.orEmpty(), avatarSize = LocalMediumAvatarSize.current.size)
            Text(text = item.name.orEmpty(), modifier = Modifier
                .padding(LocalMediumPadding.current.size).weight(1f))
        }
    }
}