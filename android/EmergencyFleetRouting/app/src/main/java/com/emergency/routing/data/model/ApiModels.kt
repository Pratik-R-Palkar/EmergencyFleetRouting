package com.emergency.routing.data.model

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("address") val address: String? = null
)

data class EmergencyRequest(
    @SerializedName("emergency_type") val emergencyType: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: Coordinates,
    @SerializedName("reporter_contact") val reporterContact: String? = null
)

data class EmergencyResponse(
    @SerializedName("id") val id: String,
    @SerializedName("emergency_type") val emergencyType: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("status") val status: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: Coordinates,
    @SerializedName("created_at") val createdAt: String
)

data class VehicleLocation(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("bearing") val bearing: Float? = 0f,
    @SerializedName("speed_kmh") val speedKmh: Float? = 0f
)

data class VehicleResponse(
    @SerializedName("id") val id: String,
    @SerializedName("call_sign") val callSign: String,
    @SerializedName("vehicle_type") val vehicleType: String,
    @SerializedName("status") val status: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("fuel_level_percent") val fuelLevelPercent: Int,
    @SerializedName("equipment") val equipment: List<String> = emptyList(),
    @SerializedName("current_location") val currentLocation: VehicleLocation
)

data class AssignmentRequest(
    @SerializedName("emergency_id") val emergencyId: String,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("dispatcher_notes") val dispatcherNotes: String? = null
)

data class AssignmentResponse(
    @SerializedName("assignment_id") val assignmentId: String,
    @SerializedName("emergency_id") val emergencyId: String,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("status") val status: String,
    @SerializedName("route_id") val routeId: String?,
    @SerializedName("dispatched_at") val dispatchedAt: String,
    @SerializedName("eta_minutes") val etaMinutes: Double?,
    @SerializedName("dispatcher_notes") val dispatcherNotes: String? = null
)

data class RouteWaypoint(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("step") val step: Int,
    @SerializedName("instruction") val instruction: String? = null
)

data class TrafficCondition(
    @SerializedName("level") val level: String,
    @SerializedName("congestion_factor") val congestionFactor: Double,
    @SerializedName("last_updated") val lastUpdated: String
)

data class RoadClosure(
    @SerializedName("closure_id") val closureId: String,
    @SerializedName("street_name") val streetName: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("avoided") val avoided: Boolean
)

data class RouteResponse(
    @SerializedName("route_id") val routeId: String,
    @SerializedName("origin") val origin: Coordinates,
    @SerializedName("destination") val destination: Coordinates,
    @SerializedName("algorithm_used") val algorithmUsed: String,
    @SerializedName("total_distance_km") val totalDistanceKm: Double,
    @SerializedName("estimated_time_minutes") val estimatedTimeMinutes: Double,
    @SerializedName("traffic_condition") val trafficCondition: TrafficCondition,
    @SerializedName("road_closures") val roadClosures: List<RoadClosure> = emptyList(),
    @SerializedName("waypoints") val waypoints: List<RouteWaypoint> = emptyList()
)
