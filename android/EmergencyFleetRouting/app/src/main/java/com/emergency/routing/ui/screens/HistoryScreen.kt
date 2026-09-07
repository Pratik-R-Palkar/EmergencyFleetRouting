package com.emergency.routing.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class HistoryItem(
    val id: String,
    val type: String,
    val vehicle: String,
    val timestamp: String,
    val durationMin: String,
    val status: String
)

@Composable
fun HistoryScreen() {
    val mockHistory = listOf(
        HistoryItem("EMG-8801", "MEDICAL", "Ambulance Alpha", "10:15 AM Today", "6.2 mins", "COMPLETED"),
        HistoryItem("EMG-8802", "POLICE", "Police Squad 4", "09:30 AM Today", "4.0 mins", "COMPLETED"),
        HistoryItem("EMG-8799", "FIRE", "Fire Engine 1", "Yesterday", "8.5 mins", "COMPLETED")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dispatch History & Logs",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(mockHistory) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${item.id} - ${item.type}", fontWeight = FontWeight.Bold)
                            Text(text = item.status, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Dispatched: ${item.vehicle}")
                        Text(text = "Time: ${item.timestamp} | Response Time: ${item.durationMin}")
                    }
                }
            }
        }
    }
}
