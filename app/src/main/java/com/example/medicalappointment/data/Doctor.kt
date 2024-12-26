package com.example.medicalappointment.data

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("doctor_id") val doctorId: Int, // Auto-increment
    @SerializedName("full_name")val fullName: String,
    @SerializedName("years_of_experience")val yearsOfExperience: Int,
    @SerializedName("phone")val phone: String,
    @SerializedName("user_id")val userId: Int,
    @SerializedName("specialization_id")val specializationId: Int
)