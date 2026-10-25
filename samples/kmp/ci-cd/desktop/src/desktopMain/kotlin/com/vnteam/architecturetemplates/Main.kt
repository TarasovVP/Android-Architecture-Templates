package com.vnteam.architecturetemplates

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vnteam.architecturetemplates.di.initKoin
import com.vnteam.architecturetemplates.presentation.App
import com.vnteam.architecturetemplates.shared.Constants.APP_NAME
import org.koin.compose.koinInject

fun main() {
    initKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = APP_NAME) {
            App(koinInject())
        }
    }
}
