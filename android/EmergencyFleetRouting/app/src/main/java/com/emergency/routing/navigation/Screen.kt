package com.emergency.routing.navigation

sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object NewEmergency : Screen("new_emergency", "New Emergency")
    object LiveMap : Screen("live_map", "Live Map")
    object Vehicles : Screen("vehicles", "Fleet Vehicles")
    object AssignmentDetails : Screen("assignment_details/{assignmentId}", "Assignment Details") {
        fun createRoute(assignmentId: String) = "assignment_details/$assignmentId"
    }
    object History : Screen("history", "Incident History")
}
