package com.vnteam.architecturetemplates

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.vnteam.architecturetemplates.details.DetailsScreen
import com.vnteam.architecturetemplates.list.ListScreen
import org.koin.core.component.KoinComponent

sealed class NavigationScreen: Screen, KoinComponent {
    class ListContentScreen : NavigationScreen() {
        @Composable
        override fun Content() {
            ListScreen()
        }
    }
    class DetailsContentScreen(val forkId: Long) : NavigationScreen() {
        @Composable
        override fun Content() {
            DetailsScreen(forkId = forkId)
        }
    }
}
