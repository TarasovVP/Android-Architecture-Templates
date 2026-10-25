package com.vnteam.architecturetemplates.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import com.vnteam.architecturetemplates.presentation.screens.create.CreateContent
import com.vnteam.architecturetemplates.presentation.screens.create.CreateScreen
import com.vnteam.architecturetemplates.presentation.screens.details.DetailsContent
import com.vnteam.architecturetemplates.presentation.screens.details.DetailsScreen
import com.vnteam.architecturetemplates.presentation.screens.list.ListContent
import com.vnteam.architecturetemplates.presentation.screens.list.ListScreen
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import com.vnteam.architecturetemplates.shared.Constants.DEFAULT_OBJECT_ID
import com.vnteam.architecturetemplates.shared.Constants.DEMO_OBJECT_ID
import com.vnteam.architecturetemplates.shared.Constants.DEMO_OBJECT_NAME
import com.vnteam.architecturetemplates.shared.NavigationScreens

@Composable
fun AppNavigation(
    navController: NavHostController,
    screenState: MutableState<ScreenState>,
) {
    NavHost(navController = navController, startDestination = NavigationScreens.MainScreen.route) {
        composable(NavigationScreens.MainScreen.route) {
            screenState.value =
                screenState.value.copy(
                    appBarState =
                        screenState.value.appBarState.copy(
                            appBarTitle = LocalStringResources.current.APP_NAME,
                            topAppBarActionVisible = false,
                        ),
                    floatingActionState =
                        screenState.value.floatingActionState.copy(
                            floatingActionButtonVisible = true,
                            floatingActionButtonTitle = LocalStringResources.current.ADD,
                            floatingActionButtonAction = {
                                navController.navigate("${NavigationScreens.CreateScreen.route}-1")
                            },
                        ),
                )
            ListScreen(screenState, { demoObjectUI ->
                navController.navigate("${NavigationScreens.DetailsScreen.route}${demoObjectUI.demoObjectId}/${demoObjectUI.name}")
            }, { viewState, onItemClick ->
                ListContent(viewState.value, onItemClick)
            })
        }
        composable(
            "${NavigationScreens.DetailsScreen.route}{$DEMO_OBJECT_ID}/{$DEMO_OBJECT_NAME}",
            arguments =
                listOf(
                    navArgument(DEMO_OBJECT_ID) {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(DEMO_OBJECT_NAME) {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
        ) { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString(DEMO_OBJECT_ID).orEmpty()
            val demoObjectName = backStackEntry.arguments?.getString(DEMO_OBJECT_NAME).orEmpty()
            screenState.value =
                screenState.value.copy(
                    appBarState =
                        screenState.value.appBarState.copy(
                            appBarTitle = demoObjectName,
                            topAppBarActionVisible = true,
                            topAppBarAction = {
                                navController.popBackStack()
                            },
                        ),
                    floatingActionState =
                        screenState.value.floatingActionState.copy(
                            floatingActionButtonVisible = true,
                            floatingActionButtonTitle = LocalStringResources.current.EDIT,
                            floatingActionButtonAction = {
                                navController.navigate("${NavigationScreens.CreateScreen.route}$demoObjectId")
                            },
                        ),
                )
            DetailsScreen(demoObjectId, screenState) { viewState ->
                DetailsContent(viewState)
            }
        }
        composable(
            "${NavigationScreens.CreateScreen.route}{$DEMO_OBJECT_ID}",
            arguments =
                listOf(
                    navArgument(DEMO_OBJECT_ID) {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    },
                ),
        ) { backStackEntry ->
            val demoObjectId =
                backStackEntry.arguments?.getString(DEMO_OBJECT_ID)
                    .takeIf { it?.isNotEmpty() == true && it != DEFAULT_OBJECT_ID } ?: ""
            screenState.value =
                screenState.value.copy(
                    appBarState =
                        screenState.value.appBarState.copy(
                            appBarTitle =
                                if (demoObjectId.isNotEmpty()) {
                                    LocalStringResources.current.EDIT
                                } else {
                                    LocalStringResources.current.CREATE
                                },
                            topAppBarActionVisible = true,
                            topAppBarAction = {
                                navController.navigateUp()
                            },
                        ),
                    floatingActionState =
                        screenState.value.floatingActionState.copy(
                            floatingActionButtonVisible = false,
                        ),
                )
            CreateScreen(demoObjectId, screenState) { viewState, originDemoObject, onClick ->
                CreateContent(viewState, originDemoObject, onClick)
            }
        }
    }
}
