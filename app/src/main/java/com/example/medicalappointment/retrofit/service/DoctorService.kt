package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.Doctor
import retrofit2.http.GET

interface DoctorService {
    @GET("/doctors")
    suspend fun getAllDoctors(): List<Doctor>
}