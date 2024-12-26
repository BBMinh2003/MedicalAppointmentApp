package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.Specialization
import retrofit2.http.GET

interface SpecializationService {
    @GET("/specializations")
    suspend fun getAllSpecialization(): List<Specialization>
}