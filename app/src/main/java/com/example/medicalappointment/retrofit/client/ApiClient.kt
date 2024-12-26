package com.example.medicalappointment.retrofit.client
import com.example.medicalappointment.data.Specialization
import com.example.medicalappointment.presentation.screen.LocalTimeAdapter
import com.example.medicalappointment.retrofit.service.AccountService
import com.example.medicalappointment.retrofit.service.AppointmentService
import com.example.medicalappointment.retrofit.service.DoctorScheduleService
import com.example.medicalappointment.retrofit.service.DoctorService
import com.example.medicalappointment.retrofit.service.DoctorWorkTimeService
import com.example.medicalappointment.retrofit.service.PatientService
import com.example.medicalappointment.retrofit.service.ServiceService
import com.example.medicalappointment.retrofit.service.SpecializationService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime

object RetrofitClient {
    private const val BASE_URL = "http://192.168.168.1:3001"
    private val client = OkHttpClient.Builder()
        .build()

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .create()
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // Thêm client vào Retrofit
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
object ApiClient {
    val accountService: AccountService by lazy {
        RetrofitClient.retrofit.create(AccountService::class.java)
    }
    val patientService: PatientService by lazy {
        RetrofitClient.retrofit.create(PatientService::class.java)
    }
    val doctorService: DoctorService by lazy {
        RetrofitClient.retrofit.create(DoctorService::class.java)
    }
    val specializationService: SpecializationService by lazy {
        RetrofitClient.retrofit.create(SpecializationService::class.java)
    }
    val doctorScheduleService: DoctorScheduleService by lazy{
        RetrofitClient.retrofit.create(DoctorScheduleService::class.java)
    }
    val doctorWorkTimeService: DoctorWorkTimeService by lazy{
        RetrofitClient.retrofit.create(DoctorWorkTimeService::class.java)
    }
    val serviceService: ServiceService by lazy{
        RetrofitClient.retrofit.create(ServiceService::class.java)
    }
    val appointmentService: AppointmentService by lazy{
        RetrofitClient.retrofit.create(AppointmentService::class.java)
    }
}
