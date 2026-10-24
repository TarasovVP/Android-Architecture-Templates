package presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(screenState: MutableState<ScreenState>,
               onItemClick: (DemoObjectUI) -> Unit,
               content: @Composable (State<ListViewState>, onItemClick: (DemoObjectUI, String) -> Unit) -> Unit) {
    val viewModel = koinViewModel<ListViewModel>()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewState.value.takeIf { it.demoObjectUIs == null }?.let {
            viewModel.processIntent(ListIntent.ClearDemoObjects())
        }
    }

    LaunchedEffect(viewState.value.successResult) {
        if (viewState.value.successResult){
            viewModel.processIntent(ListIntent.ClearDemoObjects())
        }
    }

    LaunchedEffect(screenState.value.isScreenUpdatingNeeded) {
        if (screenState.value.isScreenUpdatingNeeded && viewState.value.demoObjectUIs != null) {
            viewModel.processIntent(ListIntent.LoadDemoObjects(true))
            screenState.value = screenState.value.copy(isScreenUpdatingNeeded = false)
        }
    }

    content(viewState) { demoObjectUI, action ->
        when (action) {
            "refresh" -> viewModel.processIntent(ListIntent.LoadDemoObjects(false))
            "details" -> onItemClick(demoObjectUI)
            "confirm_delete" -> {
                viewState.value.isConfirmationDialogVisible.value = true
                viewState.value.demoObjectToDelete = demoObjectUI.demoObjectId.orEmpty()
            }
            "delete" -> {
                viewState.value.isConfirmationDialogVisible.value = false
                viewState.value.demoObjectToDelete = ""
                viewModel.processIntent(ListIntent.DeleteDemoObject( demoObjectUI.demoObjectId.orEmpty() ))
            }
        }
    }
}