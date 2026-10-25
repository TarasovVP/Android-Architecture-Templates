package com.vnteam.architecturetemplates.presentation

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLParagraphElement
import org.w3c.dom.HTMLTextAreaElement

private const val ELEMENT_DIV = "div"
private const val ELEMENT_P = "p"
private const val ELEMENT_BUTTON = "button"
private const val ELEMENT_TEXTAREA = "textarea"

private const val STYLE_POSITION = "fixed"
private const val STYLE_LEFT = "50%"
private const val STYLE_TOP = "50%"
private const val STYLE_TRANSFORM = "translate(-50%, -50%)"
private const val STYLE_BACKGROUND_COLOR = "#fff"
private const val STYLE_PADDING = "20px"
private const val STYLE_BOX_SHADOW = "0 4px 8px rgba(0, 0, 0, 0.1)"
private const val STYLE_Z_INDEX = "1000"

private const val TEXT_CHOOSE_ACTION = "Choose an action for the text:"
private const val TEXT_OPEN_IN_BROWSER = "Open in Browser"
private const val TEXT_COPY_TO_CLIPBOARD = "Copy to Clipboard"
private const val TEXT_CANCEL = "Cancel"
private const val TEXT_COPIED = "Text copied to clipboard"
private const val TEXT_BLANK = "_blank"
private const val TEXT_COPY = "copy"

fun shareLink(url: String) {
    showShareDialog(
        url,
        onOpen = { openUrl(url) },
        onCopy = { copyToClipboard(url) },
    )
}

private fun showShareDialog(
    url: String,
    onOpen: () -> Unit = {},
    onCopy: () -> Unit = {},
) {
    val modalDiv = document.createElement(ELEMENT_DIV) as HTMLDivElement
    modalDiv.style.position = STYLE_POSITION
    modalDiv.style.left = STYLE_LEFT
    modalDiv.style.top = STYLE_TOP
    modalDiv.style.transform = STYLE_TRANSFORM
    modalDiv.style.backgroundColor = STYLE_BACKGROUND_COLOR
    modalDiv.style.padding = STYLE_PADDING
    modalDiv.style.boxShadow = STYLE_BOX_SHADOW
    modalDiv.style.zIndex = STYLE_Z_INDEX

    val message = document.createElement(ELEMENT_P) as HTMLParagraphElement
    message.textContent = TEXT_CHOOSE_ACTION
    modalDiv.appendChild(message)

    val openButton = document.createElement(ELEMENT_BUTTON) as HTMLButtonElement
    openButton.textContent = TEXT_OPEN_IN_BROWSER
    openButton.onclick = {
        onOpen()
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(openButton)

    val copyButton = document.createElement(ELEMENT_BUTTON) as HTMLButtonElement
    copyButton.textContent = TEXT_COPY_TO_CLIPBOARD
    copyButton.onclick = {
        onCopy()
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(copyButton)

    val cancelButton = document.createElement(ELEMENT_BUTTON) as HTMLButtonElement
    cancelButton.textContent = TEXT_CANCEL
    cancelButton.onclick = {
        document.body?.removeChild(modalDiv)
    }
    modalDiv.appendChild(cancelButton)

    document.body?.appendChild(modalDiv)
}

private fun openUrl(url: String) {
    window.open(url, TEXT_BLANK)
}

private fun copyToClipboard(text: String) {
    val textarea = document.createElement(ELEMENT_TEXTAREA) as HTMLTextAreaElement
    textarea.value = text
    document.body?.appendChild(textarea)
    textarea.select()
    document.execCommand(TEXT_COPY)
    document.body?.removeChild(textarea)
    window.alert(TEXT_COPIED)
}
