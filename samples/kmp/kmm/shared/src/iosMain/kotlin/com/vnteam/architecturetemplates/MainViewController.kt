package com.vnteam.architecturetemplates

import androidx.compose.ui.window.ComposeUIViewController
import com.vnteam.architecturetemplates.presentation.AppNavigation
import com.vnteam.architecturetemplates.theme.AppTheme
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    return ComposeUIViewController {
        AppTheme {
            AppNavigation()
        }
    }
}