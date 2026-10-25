package presentation.list

import components.BaseButton
import components.CircularProgress
import components.DynamicVerticalList
import components.VerticalLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import kotlinx.browser.window
import org.w3c.dom.events.Event
import presentation.viewModel


@Composable
fun ListScreen(screenState: ScreenState) {

    val viewModel = viewModel(ListViewModel::class)
    val demoObjects = viewModel.state.collectAsState()

    VerticalLayout {
        BaseButton("Load") {
            viewModel.processIntent(ListIntent.LoadDemoObjects(true))
        }
        DynamicVerticalList(demoObjects.value.demoObjectUIs?.map { it.name.orEmpty() }.orEmpty()) { itemName ->
            val item = demoObjects.value.demoObjectUIs?.find { it.name == itemName }
            window.history.pushState(null, "", "/details/${item?.demoObjectId}")
            window.dispatchEvent(Event("popstate"))
        }
        if (screenState.isProgressVisible) {
            CircularProgress()
        }
    }
}