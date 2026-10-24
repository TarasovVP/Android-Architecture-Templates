import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.vnteam.architecturetemplates.di.doInitKoin
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.koinInject
import presentation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    doInitKoin()
    onWasmReady {
        CanvasBasedWindow("Architecture Templates") {
            App(koinInject())
        }
    }
}
