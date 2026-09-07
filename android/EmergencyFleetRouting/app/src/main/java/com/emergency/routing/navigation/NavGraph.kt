package com.emergency.routing.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emergency.routing.ui.screens.AssignmentDetailsScreen
import com.emergency.routing.ui.screens.DashboardScreen
import com.emergency.routing.ui.screens.HistoryScreen
import com.emergency.routing.ui.screens.LiveMapScreen
import com.emergency.routing.ui.screens.NewEmergencyScreen
import com.emergency.routing.ui.screens.VehiclesScreen
import com.emergency.routing.viewmodel.FleetViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: FleetViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToNewEmergency = { navController.navigate(Screen.NewEmergency.route) },
                onNavigateToVehicles = { navController.navigate(Screen.Vehicles.route) },
                onNavigateToMap = { navController.navigate(Screen.LiveMap.createRoute("ROUTE-7701")) },
                onNavigateToAssignmentDetails = { assignmentId ->
                    navController.navigate(Screen.AssignmentDetails.createRoute(assignmentId))
                }
            )
        }

        composable(Screen.NewEmergency.route) {
            NewEmergencyScreen(
                viewModel = viewModel,
                onEmergencyReported = { emergencyId ->
                    navController.navigate(Screen.AssignmentDetails.createRoute("ASSIGN-$emergencyId"))
                }
            )
        }

        composable(Screen.Vehicles.route) {
            VehiclesScreen(viewModel = viewModel)
        }

        composable(Screen.History.route) {
            HistoryScreen()
        }

        composable(
            route = Screen.LiveMap.route,
            arguments = listOf(
                navArgument("routeId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            LiveMapScreen(routeId = routeId, viewModel = viewModel)
        }

        composable(
            route = Screen.AssignmentDetails.route,
            arguments = listOf(
                navArgument("assignmentId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
            AssignmentDetailsScreen(
                assignmentId = assignmentId,
                viewModel = viewModel,
                onNavigateToMap = { routeId ->
                    navController.navigate(Screen.LiveMap.createRoute(routeId))
                }
            )
        }
    }
}
