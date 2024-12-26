package com.example.medicalappointment.data

import com.google.gson.annotations.SerializedName

data class Specialization(
    @SerializedName("specialization_id")val specializationId: Int, // Auto-increment
    val name: String
)