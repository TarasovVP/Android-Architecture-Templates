import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.vnteam.architecturetemplates.di.initKoin
import com.vnteam.architecturetemplates.presentation.App
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onWasmReady {
        ComposeViewport(
            content = {
                App(koinInject())
            },
        )
    }
}
