package com.emergency.routing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emergency.routing.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.NewEmergency.route) {
            NewEmergencyScreen(navController = navController)
        }
        composable(Screen.LiveMap.route) {
            LiveMapScreen(navController = navController)
        }
        composable(Screen.Vehicles.route) {
            VehiclesScreen(navController = navController)
        }
        composable(
            route = Screen.AssignmentDetails.route,
            arguments = listOf(navArgument("assignmentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: "unknown"
            AssignmentDetailsScreen(
                navController = navController,
                assignmentId = assignmentId
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(navController = navController)
        }
    }
}
