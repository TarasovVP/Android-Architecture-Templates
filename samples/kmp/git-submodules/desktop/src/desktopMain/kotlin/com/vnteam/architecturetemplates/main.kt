package com.vnteam.architecturetemplates

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vnteam.architecturetemplates.di_desktop.doInitKoin
import com.vnteam.architecturetemplates.presentation.App
import com.vnteam.architecturetemplates.presentation.Constants.APP_NAME
import org.koin.compose.koinInject

fun main() {
    doInitKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = APP_NAME) {
            App(koinInject())
        }
    }
}