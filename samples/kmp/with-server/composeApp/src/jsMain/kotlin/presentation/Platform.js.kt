package presentation

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLParagraphElement
import org.w3c.dom.HTMLTextAreaElement

actual fun shareLink(url: String) {
    showShareDialog(url,
        onOpen = { openUrl(url) },
        onCopy = { copyToClipboard(url) })
}

private fun showShareDialog(url: String, onOpen: () -> Unit = {}, onCopy: () -> Unit = {}) {
    val modalDiv = document.createElement("div") as HTMLDivElement
    modalDiv.style.position = "fixed"
    modalDiv.style.left = "50%"
    modalDiv.style.top = "50%"
    modalDiv.style.transform = "translate(-50%, -50%)"
    modalDiv.style.backgroundColor = "#fff"
    modalDiv.style.padding = "20px"
    modalDiv.style.boxShadow = "0 4px 8px rgba(0, 0, 0, 0.1)"
    modalDiv.style.zIndex = "1000"

    val message = document.createElement("p") as HTMLParagraphElement
    message.textContent = "Choose an action for the text:"
    modalDiv.appendChild(message)

    val openButton = document.createElement("button") as HTMLButtonElement
    openButton.textContent = "Open in Browser"
    openButton.onclick = {
        onOpen()
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(openButton)

    val copyButton = document.createElement("button") as HTMLButtonElement
    copyButton.textContent = "Copy to Clipboard"
    copyButton.onclick = {
        onCopy()
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(copyButton)

    val cancelButton = document.createElement("button") as HTMLButtonElement
    cancelButton.textContent = "Cancel"
    cancelButton.onclick = {
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(cancelButton)

    document.body?.appendChild(modalDiv)
}

private fun openUrl(url: String) {
    window.open(url, "_blank")
}

private fun copyToClipboard(text: String) {
    val textarea = document.createElement("textarea") as HTMLTextAreaElement
    textarea.value = text
    document.body?.appendChild(textarea)
    textarea.select()
    document.execCommand("copy")
    document.body?.removeChild(textarea)
    window.alert("Text copied to clipboard")
}