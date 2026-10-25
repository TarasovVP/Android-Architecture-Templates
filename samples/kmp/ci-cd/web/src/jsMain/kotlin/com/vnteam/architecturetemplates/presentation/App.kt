package com.vnteam.architecturetemplates.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vnteam.architecturetemplates.UIConstants.FLOAT_8
import com.vnteam.architecturetemplates.components.LanguageSwitcherButton
import com.vnteam.architecturetemplates.components.ThemeToggleButton
import com.vnteam.architecturetemplates.isMainScreen
import com.vnteam.architecturetemplates.navigateToMain
import com.vnteam.architecturetemplates.presentation.states.screen.AppBarState
import com.vnteam.architecturetemplates.presentation.states.screen.AppMessageState
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.resources.LocalLargePadding
import com.vnteam.architecturetemplates.resources.LocalMediumTextSize
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.resources.Res
import com.vnteam.architecturetemplates.resources.android_architecture_template
import com.vnteam.architecturetemplates.resources.getStringResourcesByLocale
import com.vnteam.architecturetemplates.shared.Constants.MESSAGE_ANIMATION_DURATION
import com.vnteam.architecturetemplates.splash.SplashScreen
import com.vnteam.architecturetemplates.theme.AppTheme
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun App(appViewModel: AppViewModel) {
    val isDarkTheme = appViewModel.isDarkTheme.collectAsState()
    val language = appViewModel.language.collectAsState()
    CompositionLocalProvider(LocalStringResources provides getStringResourcesByLocale(language.value.orEmpty())) {
        isDarkTheme.value?.let {
            AppTheme(it) {
                AppContent(appViewModel)
            }
        } ?: SplashScreen()
    }
}

@Composable
fun AppContent(appViewModel: AppViewModel) {
    val screenState = appViewModel.screenState
    LaunchedEffect(screenState.value.appMessageState.messageVisible) {
        if (screenState.value.appMessageState.messageVisible) {
            delay(MESSAGE_ANIMATION_DURATION)
            screenState.value =
                screenState.value.copy(
                    appMessageState =
                        screenState.value.appMessageState.copy(
                            messageVisible = false,
                            messageText = "",
                        ),
                )
        }
    }
    Surface {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AppBar(
                    screenState.value.appBarState.topAppBarVisible,
                    appViewModel,
                    screenState.value.appBarState,
                )
                Box(modifier = Modifier.fillMaxWidth(FLOAT_8)) {
                    AppNavigation(screenState)
                }
            }
            if (screenState.value.floatingActionState.floatingActionButtonVisible) {
                ExtendedFloatingActionButton(
                    onClick = { screenState.value.floatingActionState.floatingActionButtonAction() },
                    content = { Text(screenState.value.floatingActionState.floatingActionButtonTitle) },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.White,
                    modifier =
                        Modifier
                            .padding(LocalLargePadding.current.size),
                )
            }
            Box(
                modifier =
                    Modifier.fillMaxWidth().padding(
                        horizontal = LocalDefaultPadding.current.size,
                        vertical = LocalLargePadding.current.size,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                AppSnackBar(screenState.value.appMessageState)
            }
            if (screenState.value.isProgressVisible) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun AppSnackBar(appMessageState: AppMessageState) {
    if (appMessageState.messageVisible) {
        Snackbar(
            containerColor = if (appMessageState.isMessageError) Color.Red else Color.Green,
        ) {
            Text(text = appMessageState.messageText)
        }
    }
}

@Composable
fun AppBar(
    isAppBarVisible: Boolean,
    appViewModel: AppViewModel,
    appBarState: AppBarState,
) {
    if (!isAppBarVisible) return
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier =
                Modifier
                    .padding(horizontal = LocalDefaultPadding.current.size)
                    .size(LocalLargeAvatarSize.current.size),
            onClick = {
                if (!window.isMainScreen()) window.navigateToMain()
            },
        ) {
            Image(
                painter = painterResource(Res.drawable.android_architecture_template),
                contentDescription = LocalStringResources.current.home,
            )
        }
        Text(
            text = appBarState.appBarTitle,
            fontSize = LocalMediumTextSize.current.textSize,
            color = Color.White,
            modifier = Modifier.padding(LocalLargePadding.current.size).weight(1f),
        )
        Row(modifier = Modifier.padding(horizontal = LocalLargePadding.current.size)) {
            LanguageSwitcherButton(appViewModel)
            ThemeToggleButton(appViewModel)
        }
    }
}
