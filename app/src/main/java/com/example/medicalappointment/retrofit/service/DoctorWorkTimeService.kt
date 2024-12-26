package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.DoctorSchedule
import com.example.medicalappointment.data.DoctorWorkTime
import retrofit2.http.GET

interface DoctorWorkTimeService {
    @GET("/work-times")
    suspend fun getAllDoctorWorkTime(): List<DoctorWorkTime>
}