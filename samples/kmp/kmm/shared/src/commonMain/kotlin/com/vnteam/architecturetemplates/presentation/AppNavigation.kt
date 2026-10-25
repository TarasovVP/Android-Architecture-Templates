package com.vnteam.architecturetemplates.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vnteam.architecturetemplates.presentation.details.DetailsScreen
import com.vnteam.architecturetemplates.presentation.list.ListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            ListScreen {
            val demoObjectId = it.toString()
            navController.navigate("details/$demoObjectId")
        } }
        composable("details/{demoObjectId}", arguments = listOf(navArgument("demoObjectId") { type = NavType.StringType
            defaultValue = "" })) { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString("demoObjectId").orEmpty().toLong()
            DetailsScreen(demoObjectId) {
                navController.popBackStack()
            }
        }
    }
}