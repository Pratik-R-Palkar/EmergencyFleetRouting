package com.emergency.routing.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object NewEmergency : Screen("new_emergency", "New Incident", Icons.Default.AddAlert)
    object Vehicles : Screen("vehicles", "Fleet Vehicles", Icons.Default.DirectionsCar)
    object History : Screen("history", "History", Icons.Default.History)
    object LiveMap : Screen("live_map?routeId={routeId}", "Live Map", Icons.Default.Map) {
        fun createRoute(routeId: String? = null): String {
            return if (routeId != null) "live_map?routeId=$routeId" else "live_map"
        }
    }
    object AssignmentDetails : Screen("assignment_details/{assignmentId}", "Dispatch Details") {
        fun createRoute(assignmentId: String): String = "assignment_details/$assignmentId"
    }
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.NewEmergency,
    Screen.Vehicles,
    Screen.LiveMap,
    Screen.History
)
