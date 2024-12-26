package com.example.medicalappointment.data

import java.util.Date

data class Appointment(
    val appointment_id: Int, // Auto-increment
    val status: String,
    val created_at: Date?, // Timestamp
    val patient_id: Int,
    val schedule_id: Int
)
data class AppointmentRequest(
    val status: String,
    val patient_id: Int,
    val schedule_id: Int
)
data class AppointmentResponse(
    val appointment_id: Int
)