package com.emergency.routing.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emergency.routing.ui.theme.PriorityHigh
import com.emergency.routing.viewmodel.FleetViewModel
import com.emergency.routing.viewmodel.UiState

@Composable
fun LiveMapScreen(
    routeId: String? = null,
    viewModel: FleetViewModel
) {
    val effectiveRouteId = routeId ?: "ROUTE-7701"

    LaunchedEffect(effectiveRouteId) {
        viewModel.loadRouteDetails(effectiveRouteId)
    }

    val state by viewModel.routeDetailsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Live Navigation & Traffic Route",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        when (val routeState = state) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${routeState.message}", color = MaterialTheme.colorScheme.error)
            }
            is UiState.Success -> {
                val routeData = routeState.data

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Active Route: ${routeData.routeId}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Algorithm: ${routeData.algorithmUsed}")
                        }
                        Text(
                            text = "ETA: ${routeData.estimatedTimeMinutes} mins",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (routeData.roadClosures.isNotEmpty()) {
                    val closure = routeData.roadClosures.first()
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = PriorityHigh
                            )
                            Text(
                                text = "Road Closure Avoided: ${closure.streetName} (${closure.reason}). Dynamic detour active.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFFE2E8F0), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val primaryColor = MaterialTheme.colorScheme.primary
                    val roadColor = Color(0xFF94A3B8)

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        drawLine(
                            color = roadColor,
                            start = Offset(width * 0.1f, height * 0.5f),
                            end = Offset(width * 0.9f, height * 0.5f),
                            strokeWidth = 14f
                        )
                        drawLine(
                            color = roadColor,
                            start = Offset(width * 0.5f, height * 0.1f),
                            end = Offset(width * 0.5f, height * 0.9f),
                            strokeWidth = 14f
                        )

                        drawLine(
                            color = PriorityHigh,
                            start = Offset(width * 0.5f, height * 0.5f),
                            end = Offset(width * 0.7f, height * 0.5f),
                            strokeWidth = 16f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                        )

                        val start = Offset(width * 0.15f, height * 0.8f)
                        val detourPt = Offset(width * 0.4f, height * 0.3f)
                        val end = Offset(width * 0.85f, height * 0.3f)

                        drawLine(
                            color = primaryColor,
                            start = start,
                            end = detourPt,
                            strokeWidth = 18f,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = primaryColor,
                            start = detourPt,
                            end = end,
                            strokeWidth = 18f,
                            cap = StrokeCap.Round
                        )

                        drawCircle(color = Color(0xFF1976D2), radius = 24f, center = start)
                        drawCircle(color = PriorityHigh, radius = 28f, center = end)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Navigation, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text(text = "Turn Right onto Station Flyover (300m)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
