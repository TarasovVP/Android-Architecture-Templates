package com.vnteam.architecturetemplates.presentation

import Constants.PATH_START
import Constants.POP_STATE
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import kotlinx.browser.window
import navigateTo
import org.w3c.dom.events.Event
import com.vnteam.architecturetemplates.presentation.screens.create.CreateContent
import com.vnteam.architecturetemplates.presentation.screens.create.CreateScreen
import com.vnteam.architecturetemplates.presentation.screens.details.DetailsContent
import com.vnteam.architecturetemplates.presentation.screens.details.DetailsScreen
import com.vnteam.architecturetemplates.presentation.screens.list.ListContent
import com.vnteam.architecturetemplates.presentation.screens.list.ListScreen
import com.vnteam.architecturetemplates.presentation.screens.page_not_found.PageNotFound

@Composable
fun AppNavigation(screenState: MutableState<ScreenState>) {
    val currentPath = remember { mutableStateOf(window.location.pathname) }
    DisposableEffect(Unit) {
        val onPopState: (Event) -> Unit = {
            currentPath.value = window.location.pathname
        }
        window.addEventListener(POP_STATE, onPopState)

        onDispose {
            window.removeEventListener(POP_STATE, onPopState)
        }
    }
    when  {
        currentPath.value == "" || currentPath.value == PATH_START -> {
            ListScreen(screenState, onItemClick = { demoObjectUI ->
                window.navigateTo("${NavigationScreens.DetailsScreen.route}${demoObjectUI.demoObjectId}")
            }, content = { viewState, onItemClick ->
                ListContent(viewState.value, screenState, onItemClick)
            })
        }
        currentPath.value.startsWith("$PATH_START${NavigationScreens.DetailsScreen.route}") -> {
            DetailsScreen(currentPath.value.removePrefix("$PATH_START${NavigationScreens.DetailsScreen.route}"), screenState) { viewState ->
                DetailsContent(viewState, screenState)
            }
        }
        currentPath.value.startsWith("$PATH_START${NavigationScreens.EditScreen.route}") -> {
            CreateScreen(currentPath.value.removePrefix("$PATH_START${NavigationScreens.EditScreen.route}"), screenState) { viewState, originDemoObject, onClick ->
                CreateContent(viewState, screenState, originDemoObject, onClick)
            }
        }
        currentPath.value.startsWith("$PATH_START${NavigationScreens.CreateScreen.route}") -> {
            CreateScreen("", screenState) { viewState, originDemoObject, onClick ->
                CreateContent(viewState, screenState, originDemoObject, onClick)
            }
        }
        else -> {
            PageNotFound()
        }
    }
}