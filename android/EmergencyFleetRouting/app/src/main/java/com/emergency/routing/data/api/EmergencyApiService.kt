package com.emergency.routing.data.api

import com.emergency.routing.data.model.AssignmentDetailsDto
import com.emergency.routing.data.model.AssignmentDto
import com.emergency.routing.data.model.CreateAssignmentRequest
import com.emergency.routing.data.model.CreateEmergencyRequest
import com.emergency.routing.data.model.EmergencyDto
import com.emergency.routing.data.model.RouteDto
import com.emergency.routing.data.model.VehicleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EmergencyApiService {

    @POST("emergencies")
    suspend fun reportEmergency(
        @Body request: CreateEmergencyRequest
    ): Response<EmergencyDto>

    @GET("vehicles")
    suspend fun getVehicles(
        @Query("status") status: String? = null
    ): Response<List<VehicleDto>>

    @POST("assignments")
    suspend fun assignVehicle(
        @Body request: CreateAssignmentRequest
    ): Response<AssignmentDto>

    @GET("assignments/{assignmentId}")
    suspend fun getAssignmentDetails(
        @Path("assignmentId") assignmentId: String
    ): Response<AssignmentDetailsDto>

    @GET("routes/{routeId}")
    suspend fun getRouteDetails(
        @Path("routeId") routeId: String
    ): Response<RouteDto>
}
