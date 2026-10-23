package com.vnteam.architecturetemplates.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vnteam.architecturetemplates.presentation.details.DetailsScreen
import com.vnteam.architecturetemplates.presentation.list.ListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") { ListScreen() {
            navController.navigate("details/$it")
        } }
        composable("details/{demoObjectId}") { backStackEntry ->
            val demoObjectId = backStackEntry.arguments?.getString("demoObjectId").orEmpty().toLong()
            DetailsScreen(demoObjectId) {
                navController.popBackStack()
            }
        }
    }
}