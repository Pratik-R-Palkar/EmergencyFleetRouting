package com.emergency.routing.data.model

import com.google.gson.annotations.SerializedName

// Location DTO
data class LocationDto(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("address") val address: String? = null
)

// Emergency Request & Response
data class CreateEmergencyRequest(
    @SerializedName("emergency_type") val emergencyType: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("reporter_contact") val reporterContact: String
)

data class EmergencyDto(
    @SerializedName("id") val id: String,
    @SerializedName("emergency_type") val emergencyType: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("reporter_contact") val reporterContact: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)

// Vehicle DTO
data class VehicleDto(
    @SerializedName("id") val id: String,
    @SerializedName("call_sign") val callSign: String,
    @SerializedName("vehicle_type") val vehicleType: String,
    @SerializedName("status") val status: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("fuel_level_percent") val fuelLevelPercent: Int,
    @SerializedName("current_location") val currentLocation: LocationDto,
    @SerializedName("equipment") val equipment: List<String> = emptyList()
)

// Assignment Request & Response
data class CreateAssignmentRequest(
    @SerializedName("emergency_id") val emergencyId: String,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("dispatcher_notes") val dispatcherNotes: String? = null
)

data class AssignmentDto(
    @SerializedName("assignment_id") val assignmentId: String,
    @SerializedName("emergency_id") val emergencyId: String,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("status") val status: String,
    @SerializedName("route_id") val routeId: String? = null,
    @SerializedName("dispatched_at") val dispatchedAt: String? = null,
    @SerializedName("eta_minutes") val etaMinutes: Double? = null,
    @SerializedName("dispatcher_notes") val dispatcherNotes: String? = null
)

data class AssignmentDetailsDto(
    @SerializedName("assignment_id") val assignmentId: String,
    @SerializedName("status") val status: String,
    @SerializedName("route_id") val routeId: String? = null,
    @SerializedName("emergency") val emergency: EmergencyDto? = null,
    @SerializedName("vehicle") val vehicle: VehicleDto? = null,
    @SerializedName("dispatched_at") val dispatchedAt: String? = null,
    @SerializedName("eta_minutes") val etaMinutes: Double? = null,
    @SerializedName("completed_at") val completedAt: String? = null,
    @SerializedName("dispatcher_notes") val dispatcherNotes: String? = null
)

// Route & Traffic DTOs
data class WaypointDto(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("step") val step: Int
)

data class TrafficConditionDto(
    @SerializedName("level") val level: String,
    @SerializedName("congestion_factor") val congestionFactor: Double,
    @SerializedName("last_updated") val lastUpdated: String? = null
)

data class RoadClosureDto(
    @SerializedName("closure_id") val closureId: String,
    @SerializedName("street_name") val streetName: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("avoided") val avoided: Boolean
)

data class RouteDto(
    @SerializedName("route_id") val routeId: String,
    @SerializedName("origin") val origin: LocationDto,
    @SerializedName("destination") val destination: LocationDto,
    @SerializedName("algorithm_used") val algorithmUsed: String,
    @SerializedName("total_distance_km") val totalDistanceKm: Double,
    @SerializedName("estimated_time_minutes") val estimatedTimeMinutes: Double,
    @SerializedName("traffic_condition") val trafficCondition: TrafficConditionDto? = null,
    @SerializedName("road_closures") val roadClosures: List<RoadClosureDto> = emptyList(),
    @SerializedName("waypoints") val waypoints: List<WaypointDto> = emptyList()
)
