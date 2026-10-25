package com.vnteam.architecturetemplates.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.vnteam.architecturetemplates.navigation.AppNavigation
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.resources.LocalLargerPadding
import com.vnteam.architecturetemplates.resources.LocalSmallPadding
import com.vnteam.architecturetemplates.resources.LocalStringResources

@Composable
fun ScaffoldContent(appViewModel: AppViewModel) {
    val screenState = remember { mutableStateOf(ScreenState()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    LaunchedEffect(screenState.value.appMessageState.messageVisible) {
        if (screenState.value.appMessageState.messageVisible) {
            snackbarHostState.showSnackbar(
                message = screenState.value.appMessageState.messageText,
                duration = SnackbarDuration.Short,
            )
            screenState.value =
                screenState.value.copy(
                    appMessageState = screenState.value.appMessageState.copy(messageVisible = false),
                )
        }
    }

    Scaffold(
        topBar = { AppTopBar(screenState.value, appViewModel) },
        snackbarHost = {
            AppSnackbarHost(
                snackbarHostState,
                screenState.value.appMessageState.isMessageError,
            )
        },
        floatingActionButton = {
            AppFAB(screenState.value)
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavigation(navController, screenState)
                if (screenState.value.isProgressVisible) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    screenState: ScreenState,
    appViewModel: AppViewModel,
) {
    TopAppBar(
        title = { Text(screenState.appBarState.appBarTitle) },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = Color.White,
            ),
        navigationIcon = {
            if (screenState.appBarState.topAppBarActionVisible) {
                IconButton(onClick = screenState.appBarState.topAppBarAction) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = LocalStringResources.current.back,
                    )
                }
            }
        },
        actions = {
            if (!screenState.appBarState.topAppBarActionVisible) {
                LanguageSwitcherButton(appViewModel)
                ThemeToggleButton(appViewModel)
            }
        },
    )
}

@Composable
fun AppFAB(screenState: ScreenState) {
    if (screenState.floatingActionState.floatingActionButtonVisible) {
        ExtendedFloatingActionButton(
            onClick = { screenState.floatingActionState.floatingActionButtonAction() },
            content = { Text(screenState.floatingActionState.floatingActionButtonTitle) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            modifier =
                Modifier.padding(
                    horizontal = LocalLargerPadding.current.size,
                    vertical = LocalSmallPadding.current.size,
                ),
        )
    }
}

@Composable
fun AppSnackbarHost(
    snackbarHostState: SnackbarHostState,
    isError: Boolean,
) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(
            snackbarData = data,
            actionColor = Color.White,
            containerColor = if (isError) Color.Red else Color.Green,
        )
    }
}
