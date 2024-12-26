package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.Patient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface PatientService {
    @GET("/patients/user/{userId}")
    suspend fun getPatientByUserId(@Path("userId") userId: Int): Patient
    @PUT("/patients/{patientId}")
    suspend fun updatePatient(@Body patient: Patient, @Path("patientId") id: Int): Patient
}