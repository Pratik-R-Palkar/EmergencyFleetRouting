package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emergency.routing.ui.theme.PriorityHigh
import com.emergency.routing.viewmodel.FleetViewModel
import com.emergency.routing.viewmodel.UiState

@Composable
fun AssignmentDetailsScreen(
    assignmentId: String,
    viewModel: FleetViewModel,
    onNavigateToMap: (String) -> Unit
) {
    LaunchedEffect(assignmentId) {
        viewModel.loadAssignmentDetails(assignmentId)
    }

    val state by viewModel.assignmentDetailsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dispatch Assignment: $assignmentId",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        when (val detailsState = state) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${detailsState.message}", color = MaterialTheme.colorScheme.error)
            }
            is UiState.Success -> {
                val details = detailsState.data
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Status: ${details.status}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        details.emergency?.let { emg ->
                            Text(text = "Emergency ID: ${emg.id}")
                            Text(text = "Type: ${emg.emergencyType} (Priority ${emg.priority})", color = PriorityHigh, fontWeight = FontWeight.Bold)
                            Text(text = "Location: ${emg.location.address ?: "Coordinates (${emg.location.latitude}, ${emg.location.longitude})"}")
                        }
                        details.vehicle?.let { veh ->
                            Text(text = "Assigned Vehicle: ${veh.callSign} (${veh.id})")
                        }
                        Text(text = "Assigned Route ID: ${details.routeId ?: "ROUTE-7701"}")
                        Text(text = "Estimated Time of Arrival (ETA): ${details.etaMinutes ?: 4.5} minutes")
                        details.dispatcherNotes?.let { notes ->
                            Text(text = "Dispatcher Notes: $notes")
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onNavigateToMap(details.routeId ?: "ROUTE-7701") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Map, contentDescription = null)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text("Open Live Route Map")
                    }
                }
            }
        }
    }
}
