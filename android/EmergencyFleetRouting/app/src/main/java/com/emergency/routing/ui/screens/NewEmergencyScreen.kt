package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emergency.routing.ui.theme.PriorityHigh
import com.emergency.routing.viewmodel.FleetViewModel
import com.emergency.routing.viewmodel.UiState

@Composable
fun NewEmergencyScreen(
    viewModel: FleetViewModel,
    onEmergencyReported: (String) -> Unit
) {
    var emergencyType by remember { mutableStateOf("FIRE") }
    var priority by remember { mutableStateOf("1") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("Central Station Road") }
    var latitude by remember { mutableStateOf("18.5204") }
    var longitude by remember { mutableStateOf("73.8567") }
    var reporterContact by remember { mutableStateOf("+919876543210") }

    val emergencyTypes = listOf("FIRE", "MEDICAL", "ACCIDENT", "POLICE")
    val priorities = listOf("1 (Critical)", "2 (High)", "3 (Medium)")
    val resultState by viewModel.newEmergencyResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Report Emergency Incident",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Emergency Type",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    emergencyTypes.forEach { type ->
                        FilterChip(
                            selected = emergencyType == type,
                            onClick = { emergencyType = type },
                            label = { Text(type) }
                        )
                    }
                }

                Text(
                    text = "Priority Level",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    priorities.forEachIndexed { index, pText ->
                        val pValue = (index + 1).toString()
                        FilterChip(
                            selected = priority == pValue,
                            onClick = { priority = pValue },
                            label = { Text(pText) }
                        )
                    }
                }

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Location Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Incident Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = reporterContact,
                    onValueChange = { reporterContact = it },
                    label = { Text("Reporter Contact Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                val isLoading = resultState is UiState.Loading
                Button(
                    onClick = {
                        val pInt = priority.toIntOrNull() ?: 1
                        val latD = latitude.toDoubleOrNull() ?: 18.5204
                        val lngD = longitude.toDoubleOrNull() ?: 73.8567
                        viewModel.reportEmergency(
                            type = emergencyType,
                            priority = pInt,
                            description = description.ifBlank { "Reported Emergency" },
                            lat = latD,
                            lng = lngD,
                            address = address,
                            contact = reporterContact,
                            onSuccess = onEmergencyReported
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PriorityHigh)
                ) {
                    Text(
                        text = if (isLoading) "Submitting..." else "Submit & Dispatch Fleet",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
