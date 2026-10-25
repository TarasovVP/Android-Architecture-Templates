package presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.vnteam.architecturetemplates.presentation.intents.DetailsIntent
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import com.vnteam.architecturetemplates.presentation.viewmodels.DetailsViewModel
import components.AppStyles
import components.BaseButton
import components.CircularProgress
import components.OwnerCard
import components.VerticalLayout
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.Event
import presentation.viewModel

@Composable
fun DetailsScreen(itemId: String, screenState: ScreenState) {
    Style(AppStyles)
    val viewModel = viewModel(DetailsViewModel::class)

    val detailsViewStateState = viewModel.state.collectAsState()
    val messageState = mutableStateOf(screenState.appMessageState)

    LaunchedEffect(itemId) {
        viewModel.processIntent(DetailsIntent.LoadDemoObject(itemId))
    }

    LaunchedEffect(detailsViewStateState.value) {
        messageState.value = screenState.appMessageState
    }
    console.log("DetailsScreen ${detailsViewStateState.value.demoObjectUI?.name}")
    VerticalLayout {
        Div(attrs = { classes(AppStyles.textStyle) }) { Text(detailsViewStateState.value.demoObjectUI?.name.orEmpty()) }
        Div(attrs = { classes(AppStyles.textStyle) }) { Text(detailsViewStateState.value.demoObjectUI?.owner?.login.orEmpty()) }
        OwnerCard(detailsViewStateState.value.demoObjectUI?.owner)
        Div(attrs = { classes(AppStyles.textStyle) }) { Text("Description:") }
        Div(attrs = { classes(AppStyles.textStyle) }) { Text(detailsViewStateState.value.demoObjectUI?.description.orEmpty()) }
        BaseButton("Back") {
            window.history.pushState(null, "", "/list")
            window.dispatchEvent(Event("popstate"))
        }

        if (screenState.isProgressVisible) {
            CircularProgress()
        }
    }
}