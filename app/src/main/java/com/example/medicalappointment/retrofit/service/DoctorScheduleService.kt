package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.Doctor
import com.example.medicalappointment.data.DoctorSchedule
import retrofit2.http.GET

interface DoctorScheduleService {
    @GET("/schedules")
    suspend fun getAllDoctorSchedules(): List<DoctorSchedule>
}