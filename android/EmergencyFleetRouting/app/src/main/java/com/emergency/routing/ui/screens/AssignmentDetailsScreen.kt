package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emergency.routing.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailsScreen(
    navController: NavController,
    assignmentId: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignment: $assignmentId") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dispatch Status: EN ROUTE", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Incident: emg-9481b7a2 (FIRE - Priority 1)")
                    Text("• Assigned Unit: Engine-1 (Fire Truck)")
                    Text("• Route ID: rte-551a82f3")
                    Text("• Dispatched At: 18:30:45 UTC")
                    Text("• Estimated ETA: 5.2 minutes")
                    Text("• Notes: High-priority dispatch; avoid Mission St closure.")
                }
            }

            Button(
                onClick = { navController.navigate(Screen.LiveMap.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Track Navigation on Map")
            }

            OutlinedButton(
                onClick = {
                    // TODO (Member 1): API call to mark arrived/completed
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mark Unit Arrived on Scene")
            }
        }
    }
}
