
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import components.Toast
import kotlinx.browser.window
import org.w3c.dom.events.Event
import presentation.details.DetailsScreen
import presentation.list.ListScreen


@Composable
fun AppContent(screenState: ScreenState) {

    val currentPath = remember { mutableStateOf(window.location.pathname) }

    DisposableEffect(Unit) {
        val onPopState: (Event) -> Unit = {
            currentPath.value = window.location.pathname
        }

        window.addEventListener("popstate", onPopState)

        onDispose {
            window.removeEventListener("popstate", onPopState)
        }
    }
    screenState.appMessageState.messageText.takeIf {
        it.isNotBlank()
    }?.let {
        Toast(it) {
            screenState.appMessageState.messageText = ""
        }
    }
    when {
        currentPath.value.startsWith("/details/") -> {
            val itemId = currentPath.value.removePrefix("/details/")
            DetailsScreen(itemId, screenState)
        }
        currentPath.value == "/list" || currentPath.value.isBlank() || currentPath.value == "/" -> {
            ListScreen(screenState)
        }
    }
}