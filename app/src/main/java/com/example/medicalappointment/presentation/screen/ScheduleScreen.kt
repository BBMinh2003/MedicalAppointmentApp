package com.example.medicalappointment.presentation.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.data.Appointment
import com.example.medicalappointment.data.Doctor
import com.example.medicalappointment.data.DoctorSchedule
import com.example.medicalappointment.data.DoctorWorkTime
import com.example.medicalappointment.data.Specialization
import com.example.medicalappointment.presentation.components.ScheduleDoctorCard
import com.example.medicalappointment.retrofit.client.ApiClient.appointmentService
import com.example.medicalappointment.retrofit.client.ApiClient.doctorScheduleService
import com.example.medicalappointment.retrofit.client.ApiClient.doctorService
import com.example.medicalappointment.retrofit.client.ApiClient.doctorWorkTimeService
import com.example.medicalappointment.retrofit.client.ApiClient.specializationService
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.example.medicalappointment.data.AppointmentService
import com.example.medicalappointment.data.AppointmentWithServices
import com.example.medicalappointment.data.Service
import com.example.medicalappointment.retrofit.client.ApiClient
import com.example.medicalappointment.retrofit.service.ServiceService

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onError: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val appointments = remember { mutableStateOf<List<Appointment>>(emptyList()) }
    val doctors = remember { mutableStateOf<List<Doctor>>(emptyList()) }
    val schedules = remember { mutableStateOf<List<DoctorSchedule>>(emptyList()) }
    val specializations = remember { mutableStateOf<List<Specialization>>(emptyList()) }
    val workTimes = remember { mutableStateOf<List<DoctorWorkTime>>(emptyList()) }
    val services = remember { mutableStateOf<List<Service>>(emptyList()) }
    val appointmentServices = remember { mutableStateOf<List<AppointmentService>>(emptyList()) }

    var selectedStatus by remember { mutableStateOf("Pending") } // Trạng thái lọc
    val Appointments = remember { mutableStateListOf<Appointment>() }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val allAppointments = appointmentService.getAllAppointment()
                Appointments.addAll(allAppointments)
                appointments.value = appointmentService.getAllAppointment()
                doctors.value = doctorService.getAllDoctors()
                schedules.value = doctorScheduleService.getAllDoctorSchedules()
                specializations.value = specializationService.getAllSpecialization()
                workTimes.value = doctorWorkTimeService.getAllDoctorWorkTime()
                services.value = ApiClient.serviceService.getAllServices()
                appointmentServices.value = ApiClient.serviceService.getAllAppointmentServices()
            } catch (e: Exception) {
                onError("Error loading data: ${e.localizedMessage}")
            }
        }
    }

    // Lọc danh sách theo trạng thái
    val filteredAppointments = Appointments.filter {
        if (selectedStatus == "Upcoming") it.status == "Pending" else it.status == "Confirmed"
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Upcoming Schedule
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 20.dp)
                    .weight(1f)
                    .clickable { selectedStatus = "Upcoming" },
                color = if (selectedStatus == "Upcoming") BluePrimary else Color(0xFF63B4FF).copy(alpha = 0.1f),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
                    text = "Upcoming Schedule",
                    fontFamily = poppinsFontFamily,
                    color = if (selectedStatus == "Upcoming") Color.White else BluePrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            // Completed Schedule
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 20.dp)
                    .weight(1f)
                    .clickable { selectedStatus = "Completed" },
                color = if (selectedStatus == "Completed") BluePrimary else Color(0xFF63B4FF).copy(alpha = 0.1f),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
                    text = "Completed Schedule",
                    fontFamily = poppinsFontFamily,
                    color = if (selectedStatus == "Completed") Color.White else BluePrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }

        // LazyColumn for ScheduleDoctorCard
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredAppointments) { appointment ->
                val schedule = schedules.value.find { it.schedule_id == appointment.schedule_id }
                val doctor = doctors.value.find { it.doctorId == schedule?.doctor_id }
                val workTime = workTimes.value.find { it.work_time_id == schedule?.work_time_id }
                val specialization = specializations.value.find { it.specializationId == doctor?.specializationId }
                val appointmentServiceIds = appointmentServices.value.filter { it.appointment_id == appointment.appointment_id }.map { it.service_id }
                val appointmentServiceNames = services.value.filter { it.service_id in appointmentServiceIds }.joinToString(", ") { it.service_name }

                Log.d("Appointment", appointment.toString())
                Log.d("Schedule", schedule?.toString() ?: "Schedule not found")
                Log.d("Doctor", doctor?.toString() ?: "Doctor not found")
                Log.d("WorkTime", workTime?.toString() ?: "WorkTime not found")
                Log.d("Specialization", specialization?.toString() ?: "Specialization not found")
                ScheduleDoctorCard(
                    doctorName = doctor?.fullName.orEmpty(),
                    specialization = specialization?.name.orEmpty(),
                    workTime = workTime?.let { "${it.start_time} - ${it.end_time}" }.orEmpty(),
                    status = appointment.status,
                    services = appointmentServiceNames,
                    onDetailClick = {
                        // Navigate to detail screen or handle the click
                    }
                )
            }
        }
    }
}
class AppointmentRepository(private val api: ServiceService) {
    suspend fun getAppointmentsWithServices(): List<AppointmentWithServices> {
        val services = api.getAllServices()
        val appointmentServices = api.getAllAppointmentServices()

        return appointmentServices.groupBy { it.appointment_id }.map { (appointmentId, serviceMappings) ->
            AppointmentWithServices(
                appointmentId = appointmentId,
                services = serviceMappings.mapNotNull { mapping ->
                    services.find { it.service_id == mapping.service_id }
                }
            )
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun ScheduleScreenPreview() {
//    ScheduleScreen()
//}