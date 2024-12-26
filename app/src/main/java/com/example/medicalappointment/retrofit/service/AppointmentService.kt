package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.Appointment
import com.example.medicalappointment.data.AppointmentRequest
import com.example.medicalappointment.data.AppointmentResponse
import com.example.medicalappointment.data.AppointmentService
import com.example.medicalappointment.data.RegisterRequest
import com.example.medicalappointment.data.RegisterResponse
import com.example.medicalappointment.data.Service
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentService {
    @GET("/appointments")
    suspend fun getAllAppointment(): List<Appointment>
    @POST("/appointments")
    suspend fun addAppoinment(@Body request: AppointmentRequest): Response<AppointmentResponse>
    @DELETE("/appointments/{id}")
    suspend fun deleteAppointment(@Path("id") id: Int) : AppointmentResponse
}