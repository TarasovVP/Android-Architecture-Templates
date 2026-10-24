import Constants.PATH_START
import Constants.POP_STATE
import org.w3c.dom.Window
import org.w3c.dom.events.Event

fun Window.isMainScreen(): Boolean {
    return window.location.pathname == PATH_START
}

fun Window.navigateTo(path: String) {
    history.pushState(null, "", path)
    dispatchEvent(Event(POP_STATE))
}

fun Window.navigateUp() {
    history.back()
}

fun Window.navigateToMain() {
    history.replaceState(null, "", PATH_START)
    dispatchEvent(Event(POP_STATE))
}