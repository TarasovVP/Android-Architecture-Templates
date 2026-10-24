package presentation

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.net.URI
import javax.swing.JOptionPane
import java.awt.Desktop

actual fun shareLink(url: String) {
    val options = arrayOf("Open in Browser", "Copy to Clipboard", "Cancel")
    val choice = JOptionPane.showOptionDialog(
        null,
        "Choose an action for the text:",
        "Share Text",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]
    )

    when (choice) {
        0 -> openInBrowser(url)
        1 -> copyToClipboard(url)
        else -> {
            // Do nothing
        }
    }
}

private fun openInBrowser(url: String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun copyToClipboard(text: String) {
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val selection = StringSelection(text)
    clipboard.setContents(selection, selection)
}