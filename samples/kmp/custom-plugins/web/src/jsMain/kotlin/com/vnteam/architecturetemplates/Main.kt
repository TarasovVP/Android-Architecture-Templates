import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.vnteam.architecturetemplates.di.doInitKoin
import com.vnteam.architecturetemplates.presentation.App
import com.vnteam.architecturetemplates.shared.Constants.APP_NAME
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    doInitKoin()
    onWasmReady {
        CanvasBasedWindow(APP_NAME) {
            App(koinInject())
        }
    }
}
