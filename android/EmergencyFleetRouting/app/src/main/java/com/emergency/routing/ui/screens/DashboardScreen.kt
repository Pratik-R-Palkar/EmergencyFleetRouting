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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emergency.routing.ui.theme.PriorityHigh
import com.emergency.routing.viewmodel.FleetViewModel
import com.emergency.routing.viewmodel.UiState

@Composable
fun DashboardScreen(
    viewModel: FleetViewModel,
    onNavigateToNewEmergency: () -> Unit,
    onNavigateToVehicles: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToAssignmentDetails: (String) -> Unit
) {
    val dashboardState by viewModel.dashboardState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Emergency Fleet Dispatch",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Monitor live incidents, dispatch ambulances/fire/police, and track optimal dynamic routing.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToNewEmergency,
                    colors = ButtonDefaults.buttonColors(containerColor = PriorityHigh)
                ) {
                    Icon(imageVector = Icons.Default.AddAlert, contentDescription = null)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Report New Emergency", fontWeight = FontWeight.Bold)
                }
            }
        }

        when (val state = dashboardState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is UiState.Success -> {
                val data = state.data
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        title = "Active Incidents",
                        value = "${data.activeIncidentCount}",
                        icon = Icons.Default.Warning,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Fleet Available",
                        value = "${data.availableVehicleCount} / ${data.totalVehicleCount}",
                        icon = Icons.Default.DirectionsCar,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Active Dispatches",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                val activeAssignment = data.activeAssignment
                if (activeAssignment != null) {
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
                                    text = activeAssignment.emergency?.id ?: "FIRE-2025-001",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = PriorityHigh
                                )
                                Text(
                                    text = "ETA: ${activeAssignment.etaMinutes ?: 4.5} mins",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = activeAssignment.emergency?.description ?: "Building Fire reported near Central Station",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onNavigateToAssignmentDetails(activeAssignment.assignmentId) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("View Details")
                                }
                                OutlinedButton(
                                    onClick = onNavigateToMap,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(imageVector = Icons.Default.Map, contentDescription = null)
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text("Live Route")
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No active emergency dispatches.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
