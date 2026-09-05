package com.emergency.routing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emergency.routing.data.api.RetrofitClient
import com.emergency.routing.data.model.EmergencyResponse
import com.emergency.routing.data.model.VehicleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardUiState(
    val isLoading: Boolean = false,
    val emergencies: List<EmergencyResponse> = emptyList(),
    val vehicles: List<VehicleResponse> = emptyList(),
    val errorMessage: String? = null
)

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Member 1: Uncomment and connect with live backend server
                // val emgResponse = RetrofitClient.apiService.getEmergencies()
                // val vehResponse = RetrofitClient.apiService.getVehicles()
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Failed to connect to backend"
                )
            }
        }
    }
}
