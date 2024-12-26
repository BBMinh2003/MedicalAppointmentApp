package com.example.medicalappointment.data

import java.util.Date

data class DoctorSchedule(
    val schedule_id: Int, // Auto-increment
    val work_date: Date, // Date
    val doctor_id: Int,
    val work_time_id: Int,
    val is_available: Boolean,
    val createdAt: Date? // Timestamp
)