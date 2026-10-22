package com.vnteam.architecturetemplates.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.vnteam.architecturetemplates.components.ScaffoldContent
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.resources.getStringResourcesByLocale
import com.vnteam.architecturetemplates.splash.SplashScreen
import com.vnteam.architecturetemplates.theme.AppTheme

@Composable
fun App(appViewModel: AppViewModel) {
    val isDarkTheme = appViewModel.isDarkTheme.collectAsState()
    val language = appViewModel.language.collectAsState()
    CompositionLocalProvider(LocalStringResources provides getStringResourcesByLocale(language.value.orEmpty())) {
        isDarkTheme.value?.let {
            AppTheme(it) {
                ScaffoldContent(appViewModel)
            }
        } ?: SplashScreen()
    }
}
