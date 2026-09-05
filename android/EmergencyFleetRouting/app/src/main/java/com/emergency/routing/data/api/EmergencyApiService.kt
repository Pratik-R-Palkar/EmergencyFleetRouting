package com.emergency.routing.data.api

import com.emergency.routing.data.model.AssignmentRequest
import com.emergency.routing.data.model.AssignmentResponse
import com.emergency.routing.data.model.EmergencyRequest
import com.emergency.routing.data.model.EmergencyResponse
import com.emergency.routing.data.model.RouteResponse
import com.emergency.routing.data.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit REST client interface for backend communication.
 * Module Owner: Member 1 (Android UI & integration)
 */
interface EmergencyApiService {

    @POST("emergencies")
    suspend fun createEmergency(
        @Body request: EmergencyRequest
    ): Response<EmergencyResponse>

    @GET("emergencies")
    suspend fun getEmergencies(): Response<List<EmergencyResponse>>

    @GET("vehicles")
    suspend fun getVehicles(
        @Query("status") status: String? = null
    ): Response<List<VehicleResponse>>

    @POST("assignments")
    suspend fun createAssignment(
        @Body request: AssignmentRequest
    ): Response<AssignmentResponse>

    @GET("assignments/{assignment_id}")
    suspend fun getAssignment(
        @Path("assignment_id") assignmentId: String
    ): Response<AssignmentResponse>

    @GET("routes/{route_id}")
    suspend fun getRoute(
        @Path("route_id") routeId: String
    ): Response<RouteResponse>
}
