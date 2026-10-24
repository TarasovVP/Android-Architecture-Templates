package com.vnteam.architecturetemplates.presentation

import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import javax.swing.JOptionPane

private const val OPTION_OPEN_IN_BROWSER = "Open in Browser"
private const val OPTION_COPY_TO_CLIPBOARD = "Copy to Clipboard"
private const val OPTION_CANCEL = "Cancel"
private const val DIALOG_MESSAGE = "Choose an action for the text:"
private const val DIALOG_TITLE = "Share Text"

fun shareLink(url: String) {
    val options = arrayOf(OPTION_OPEN_IN_BROWSER, OPTION_COPY_TO_CLIPBOARD, OPTION_CANCEL)

    val choice =
        JOptionPane.showOptionDialog(
            null,
            DIALOG_MESSAGE,
            DIALOG_TITLE,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0],
        )

    when (choice) {
        0 -> openInBrowser(url)
        1 -> copyToClipboard(url)
        else -> {
            // Do nothing
        }
    }
}

private fun openInBrowser(
    url: String,
    onError: (String) -> Unit = {},
) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: IOException) {
            onError.invoke(e.message.orEmpty())
        } catch (e: URISyntaxException) {
            onError.invoke(e.message.orEmpty())
        }
    }
}

private fun copyToClipboard(text: String) {
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val selection = StringSelection(text)
    clipboard.setContents(selection, selection)
}
