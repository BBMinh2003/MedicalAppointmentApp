package com.example.medicalappointment.data

import java.time.LocalTime

data class DoctorWorkTime(
    val work_time_id: Int,
    val start_time: LocalTime,
    val end_time: LocalTime
)