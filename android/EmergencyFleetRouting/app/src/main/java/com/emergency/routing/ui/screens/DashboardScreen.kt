package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emergency.routing.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Dispatch Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Fleet Status Overview",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Active Incidents: 2 (Pending: 1, Assigned: 1)")
                    Text("• Available Fleet Units: 3 (Ambulance, Fire, Police)")
                    Text("• Active Road Closures: 1")
                }
            }

            Button(
                onClick = { navController.navigate(Screen.NewEmergency.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Report New Emergency")
            }

            Button(
                onClick = { navController.navigate(Screen.LiveMap.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Live Routing Map")
            }

            Button(
                onClick = { navController.navigate(Screen.Vehicles.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage Fleet Vehicles")
            }

            Button(
                onClick = { navController.navigate(Screen.AssignmentDetails.createRoute("asg-7c103e91")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Active Assignment Details")
            }

            Button(
                onClick = { navController.navigate(Screen.History.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Incident History")
            }
        }
    }
}
