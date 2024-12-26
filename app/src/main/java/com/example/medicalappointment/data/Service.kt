package com.example.medicalappointment.data

import java.util.Date

data class Service(
    val service_id: Int,
    val service_name: String,
    val description: String,
    val created_at: Date?
)