
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vnteam.architecturetemplates.di_desktop.doInitKoin
import org.koin.compose.koinInject
import presentation.App

fun main() {
    doInitKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = "Architecture Templates") {
            App(koinInject())
        }
    }
}