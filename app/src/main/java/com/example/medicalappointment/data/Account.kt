package com.example.medicalappointment.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Account(
    val userId: Int, // Auto-increment
    val username: String,
    val password: String,
    val email: String,
    val role: String,
    val createdAt: String? = null // Timestamp
)
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)
data class RegisterResponse(
    val user_id: Int
)
data class LoginRequest(
    val username: String,
    val password: String
)
data class LoginResponse(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("created_at") val createdAt: Date? // Chấp nhận null nếu có thể không được trả về
)