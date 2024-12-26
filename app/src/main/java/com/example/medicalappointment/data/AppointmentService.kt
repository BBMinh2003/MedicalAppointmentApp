package com.example.medicalappointment.data

data class AppointmentService(
    val appointment_id: Int,
    val service_id: Int
)
data class AppointmentWithServices(
    val appointmentId: Int,
    val services: List<Service>
)
