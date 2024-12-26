package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.AppointmentRequest
import com.example.medicalappointment.data.AppointmentResponse
import com.example.medicalappointment.data.AppointmentService
import com.example.medicalappointment.data.Doctor
import com.example.medicalappointment.data.Service
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceService {
    @GET("/services")
    suspend fun getAllServices(): List<Service>
    @GET("/appointment-services")
    suspend fun getAllAppointmentServices(): List<AppointmentService>
    @POST("/appointment-services")
    suspend fun addAppoinmentServices(@Body request: AppointmentService): Response<AppointmentService>
}