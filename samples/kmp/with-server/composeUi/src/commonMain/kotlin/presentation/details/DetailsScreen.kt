package presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import com.vnteam.architecturetemplates.presentation.intents.DetailsIntent
import com.vnteam.architecturetemplates.presentation.states.DetailsViewState
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import com.vnteam.architecturetemplates.presentation.viewmodels.DetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsScreen(
    demoObjectId: String?, screenState: MutableState<ScreenState>,
    content: @Composable (DetailsViewState) -> Unit
) {
    val viewModel = koinViewModel<DetailsViewModel>()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(demoObjectId) {
        viewModel.processIntent(
            DetailsIntent.LoadDemoObject(
                demoObjectId.orEmpty(),
                screenState.value.isScreenUpdatingNeeded
            )
        )
    }
    content(viewState.value)
}