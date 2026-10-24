import com.vnteam.architecturetemplates.di.doInitKoin
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable
import presentation.withViewModelStoreOwner


fun main() {
    doInitKoin()
    renderComposable(rootElementId = "content-root") {
        if (window.location.pathname == "/" || window.location.pathname.isBlank()) {
            window.history.pushState(null, "", "/list")
        }
        withViewModelStoreOwner {
            AppContent()
        }
    }
}