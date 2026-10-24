import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.vnteam.architecturetemplates.di.doInitKoin
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable
import presentation.withViewModelStoreOwner


fun main() {
    doInitKoin()
    renderComposable(rootElementId = "content-root") {
        if (window.location.pathname == "/" || window.location.pathname.isBlank()) {
            window.history.pushState(null, "", "/list")
        }
        CompositionLocalProvider(LocalDensity provides Density(window.devicePixelRatio.toFloat())) {
            withViewModelStoreOwner {
                AppContent(ScreenState())
            }
        }
    }
}