package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEmergencyScreen(navController: NavController) {
    var emergencyType by remember { mutableStateOf("FIRE") }
    var priority by remember { mutableStateOf("1") }
    var description by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("37.774929") }
    var longitude by remember { mutableStateOf("-122.419416") }
    var address by remember { mutableStateOf("Market St & 10th St, SF") }
    var reporterContact by remember { mutableStateOf("+1-555-0199") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Emergency") },
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
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Emergency Incident Details", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = emergencyType,
                onValueChange = { emergencyType = it },
                label = { Text("Emergency Type (FIRE, MEDICAL, ACCIDENT)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = priority,
                onValueChange = { priority = it },
                label = { Text("Priority Level (1: Critical - 5: Minor)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Incident Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Street Address / Landmark") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = { Text("Latitude") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = { Text("Longitude") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = reporterContact,
                onValueChange = { reporterContact = it },
                label = { Text("Reporter Contact") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // TODO (Member 1): Trigger Retrofit API POST /emergencies
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dispatch Emergency Fleet")
            }
        }
    }
}
