package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Fleet Vehicles") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                VehicleCard(
                    callSign = "Engine-1",
                    type = "FIRE_TRUCK",
                    status = "AVAILABLE",
                    fuel = 95,
                    location = "Fire Station 1 (37.7812, -122.4111)"
                )
            }
            item {
                VehicleCard(
                    callSign = "Medic-4",
                    type = "AMBULANCE",
                    status = "AVAILABLE",
                    fuel = 88,
                    location = "General Hospital (37.7654, -122.4231)"
                )
            }
            item {
                VehicleCard(
                    callSign = "Patrol-3",
                    type = "POLICE_CRUISER",
                    status = "EN_ROUTE",
                    fuel = 78,
                    location = "Market St Corridor (37.7701, -122.4150)"
                )
            }
        }
    }
}

@Composable
fun VehicleCard(
    callSign: String,
    type: String,
    status: String,
    fuel: Int,
    location: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = callSign, style = MaterialTheme.typography.titleMedium)
                Badge { Text(status) }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Type: $type")
            Text("Fuel: $fuel%")
            Text("Location: $location")
        }
    }
}
