package presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vnteam.architecturetemplates.presentation.NavigationScreens
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import presentation.create.CreateScreen
import presentation.details.DetailsScreen
import presentation.list.ListScreen
import presentation.screens.create.CreateContent
import presentation.screens.details.DetailsContent
import presentation.screens.list.ListContent

@Composable
fun AppNavigation(navController: NavHostController, screenState: MutableState<ScreenState>) {

    NavHost(navController = navController, startDestination = NavigationScreens.MainScreen.route) {
        composable(NavigationScreens.MainScreen.route) {
            screenState.value = screenState.value.copy(
                appBarState = screenState.value.appBarState.copy(
                    appBarTitle = LocalStringResources.current.APP_NAME,
                    topAppBarActionVisible = false,
                ),
                floatingActionState = screenState.value.floatingActionState.copy(
                    floatingActionButtonVisible = true,
                    floatingActionButtonTitle = LocalStringResources.current.ADD,
                    floatingActionButtonAction = {
                        navController.navigate("${NavigationScreens.CreateScreen.route}-1")
                    }
                )
            )
            ListScreen(screenState, { demoObjectUI ->
                navController.navigate("${NavigationScreens.DetailsScreen.route}${demoObjectUI.demoObjectId}/${demoObjectUI.name}")
            }, { viewState, onItemClick ->
                ListContent(viewState.value, onItemClick)
            })
        }
        composable("${NavigationScreens.DetailsScreen.route}{demoObjectId}/{demoObjectName}", arguments = listOf(navArgument("demoObjectId") {
            type = NavType.StringType
            defaultValue = ""
        }, navArgument("demoObjectName") {
            type = NavType.StringType
            defaultValue = ""
        })) { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString("demoObjectId").orEmpty()
            val demoObjectName = backStackEntry.arguments?.getString("demoObjectName").orEmpty()
            screenState.value = screenState.value.copy(
                appBarState = screenState.value.appBarState.copy(
                    appBarTitle = demoObjectName,
                    topAppBarActionVisible = true,
                    topAppBarAction = {
                        navController.popBackStack()
                    }
                ),
                floatingActionState = screenState.value.floatingActionState.copy(
                    floatingActionButtonVisible = true,
                    floatingActionButtonTitle = LocalStringResources.current.EDIT,
                    floatingActionButtonAction = {
                        navController.navigate("${NavigationScreens.CreateScreen.route}$demoObjectId")
                    }
                )
            )
            DetailsScreen(demoObjectId, screenState) { viewState ->
                DetailsContent(viewState)
            }
        }
        composable("${NavigationScreens.CreateScreen.route}{demoObjectId}", arguments = listOf(navArgument("demoObjectId") {
            type = NavType.StringType
            defaultValue = ""
            nullable = true
        })) { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString("demoObjectId").takeIf { it?.isNotEmpty() == true && it != "-1"} ?: ""
            screenState.value = screenState.value.copy(
                appBarState = screenState.value.appBarState.copy(
                    appBarTitle = if (demoObjectId.isNotEmpty()) LocalStringResources.current.EDIT else LocalStringResources.current.CREATE,
                    topAppBarActionVisible = true,
                    topAppBarAction = {
                        navController.navigateUp()
                    }
                ),
                floatingActionState = screenState.value.floatingActionState.copy(
                floatingActionButtonVisible = false
            ))
            CreateScreen(demoObjectId, screenState) { viewState, originDemoObject, onClick ->
                CreateContent(viewState, originDemoObject, onClick)
            }
        }
    }
}