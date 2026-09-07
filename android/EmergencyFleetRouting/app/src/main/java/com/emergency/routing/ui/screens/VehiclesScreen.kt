package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emergency.routing.viewmodel.FleetViewModel
import com.emergency.routing.viewmodel.UiState

@Composable
fun VehiclesScreen(viewModel: FleetViewModel) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    val vehiclesState by viewModel.vehiclesState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Emergency Fleet Vehicles",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("ALL", "AVAILABLE", "DISPATCHED", "MAINTENANCE").forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = {
                        selectedFilter = filter
                        viewModel.loadVehicles(if (filter == "ALL") null else filter)
                    },
                    label = { Text(filter) }
                )
            }
        }

        when (val state = vehiclesState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Error loading vehicles: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            is UiState.Success -> {
                val vehiclesList = state.data
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(vehiclesList) { vehicle ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${vehicle.callSign} (${vehicle.id})",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    val statusColor = when (vehicle.status) {
                                        "AVAILABLE" -> Color(0xFF2E7D32)
                                        "DISPATCHED" -> Color(0xFFD32F2F)
                                        else -> Color.Gray
                                    }
                                    Text(
                                        text = vehicle.status,
                                        color = statusColor,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "Type: ${vehicle.vehicleType} | Fuel: ${vehicle.fuelLevelPercent}% | Capacity: ${vehicle.capacity}")
                                if (vehicle.equipment.isNotEmpty()) {
                                    Text(
                                        text = "Equipment: ${vehicle.equipment.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
