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
fun HistoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incident & Dispatch History") },
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
                HistoryItem(
                    id = "emg-8821",
                    type = "MEDICAL",
                    resolvedAt = "2026-09-05 16:45 UTC",
                    unit = "Medic-4",
                    duration = "14 mins"
                )
            }
            item {
                HistoryItem(
                    id = "emg-7712",
                    type = "TRAFFIC_ACCIDENT",
                    resolvedAt = "2026-09-05 14:10 UTC",
                    unit = "Patrol-3 & Engine-1",
                    duration = "32 mins"
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    id: String,
    type: String,
    resolvedAt: String,
    unit: String,
    duration: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "$id ($type)", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Resolved: $resolvedAt")
            Text("Responding Units: $unit")
            Text("Total Response & Scene Time: $duration")
        }
    }
}
