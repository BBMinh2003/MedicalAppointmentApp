package com.example.medicalappointment.retrofit.service

import com.example.medicalappointment.data.LoginRequest
import com.example.medicalappointment.data.LoginResponse
import com.example.medicalappointment.data.RegisterRequest
import com.example.medicalappointment.data.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {
    @POST("accounts")
    suspend fun registerAccount(@Body request: RegisterRequest): Response<RegisterResponse>
    @POST("login")
    suspend fun checkLogin(@Body request: LoginRequest): Response<LoginResponse>
}