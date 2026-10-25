package presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import presentation.details.DetailsScreen
import presentation.list.ListScreen

@Composable
fun AppNavigation(screenState: MutableState<ScreenState>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            screenState.value = ScreenState().apply {
                topAppBarTitle = LocalStringResources.current.DEMO_OBJECTS
            }
            ListScreen(screenState) { demoObjectUI ->
                navController.navigate("details/${demoObjectUI.id}/${demoObjectUI.name}")
            }
        }
        composable("details/{demoObjectId}/{demoObjectName}", arguments = listOf(navArgument("demoObjectId") {
            type = NavType.StringType
            defaultValue = ""
        }, navArgument("demoObjectName") {
            type = NavType.StringType
            defaultValue = ""
        })) { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString("demoObjectId").orEmpty().toLong()
            val demoObjectName = backStackEntry.arguments?.getString("demoObjectName").orEmpty()
            screenState.value = ScreenState().apply {
                topAppBarTitle = demoObjectName
                topAppBarActionVisible = true
                topAppBarAction = {
                    navController.popBackStack()
                }
            }
            DetailsScreen(demoObjectId, screenState)
        }
    }
}