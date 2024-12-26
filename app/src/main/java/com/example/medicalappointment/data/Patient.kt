package com.example.medicalappointment.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Patient(
    @SerializedName("patient_id") val patientId: Int,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("date_of_birth") val dateOfBirth: Date?, // Date
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("user_id") val userId: Int
)