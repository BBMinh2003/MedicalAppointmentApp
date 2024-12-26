package com.example.medicalappointment.presentation.screen

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.medicalappointment.data.AppointmentRequest
import com.example.medicalappointment.data.AppointmentService
import com.example.medicalappointment.data.DoctorSchedule
import com.example.medicalappointment.data.DoctorWorkTime
import com.example.medicalappointment.data.Service
import com.example.medicalappointment.presentation.components.ServiceItem
import com.example.medicalappointment.presentation.components.WorkTimeItem
import com.example.medicalappointment.retrofit.client.ApiClient.appointmentService
import com.example.medicalappointment.retrofit.client.ApiClient.doctorScheduleService
import com.example.medicalappointment.retrofit.client.ApiClient.doctorWorkTimeService
import com.example.medicalappointment.retrofit.client.ApiClient.serviceService
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DoctorScheduleScreen(
    doctorId: Int,
    patientId: Int,
    onError: (String) -> Unit,
    navController: NavController
) {
    var schedules by remember { mutableStateOf<List<DoctorSchedule>>(emptyList()) }
    var workTimes by remember { mutableStateOf<List<DoctorWorkTime>>(emptyList()) }
    var services by remember { mutableStateOf<List<Service>>(emptyList()) }
    val selectedDate = remember { mutableStateOf<Date?>(null) }
    val selectedServices = remember { mutableStateListOf<Int>() }
    val selectedWorkTimeId = remember { mutableStateOf<Int?>(null) }

    // Lấy danh sách DoctorSchedules và DoctorWorkTimes
    LaunchedEffect(doctorId) {
        try {
            // Lấy danh sách DoctorSchedules từ API
            val scheduleResponse = doctorScheduleService.getAllDoctorSchedules()
            schedules = scheduleResponse.filter { it.doctor_id == doctorId } // Gán lại toàn bộ danh sách

            // Lấy danh sách DoctorWorkTimes từ API
            val workTimeResponse = doctorWorkTimeService.getAllDoctorWorkTime()
            workTimes = workTimeResponse // Gán lại toàn bộ danh sách workTimes
            services = serviceService.getAllServices()
        } catch (e: Exception) {
            onError("Failed to fetch doctor schedule or work time: ${e.localizedMessage}")
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(
            onClick = { navController.popBackStack() }, // Quay lại HomeScreen
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back to Home")
        }
        Text("Lịch làm việc của bác sĩ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        // Hiển thị DatePicker với các ngày làm việc
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        val availableDates = schedules.map { it.work_date }

        // Sử dụng LaunchedEffect thay vì gọi DatePicker bên trong `remember`
        val datePickerDialogState = remember { mutableStateOf(false) }
        val selectedCalendarDate = remember { mutableStateOf(currentDate) }

        // Hiển thị DatePicker khi người dùng nhấn vào nút
        Button(
            onClick = {
                datePickerDialogState.value = true
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Chọn ngày")
        }

        if (datePickerDialogState.value) {
            // Mở DatePicker dialog khi trạng thái là true
            val datePickerDialog = DatePickerDialog(
                LocalContext.current,
                { _, year, month, dayOfMonth ->
                    selectedCalendarDate.value = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time
                    datePickerDialogState.value = false
                    selectedDate.value = selectedCalendarDate.value
                },
                currentDate.year + 1900,
                currentDate.month,
                currentDate.date
            )
            datePickerDialog.show()
        }

        // Hiển thị ngày đã chọn
        Text(
            text = "Ngày đã chọn: ${selectedDate?.value?.let { formatDate(it) }}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        selectedDate.value?.let { selected ->
            val availableSchedules = schedules.filter { isSameDay(it.work_date, selected) }

            Text("Khung giờ làm việc:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            LazyRow {
                items(availableSchedules) { schedule ->
                    val workTime = workTimes.find { it.work_time_id == schedule.work_time_id }
                    WorkTimeItem(
                        workTime = workTime,
                        isAvailable = schedule.is_available,
                        isSelected = selectedWorkTimeId.value == schedule.schedule_id,
                        onClick = {
                            if (schedule.is_available) {
                                selectedWorkTimeId.value = schedule.schedule_id
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Dịch vụ:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        LazyColumn (modifier = Modifier.height(300.dp)){
            items(services) { service ->
                ServiceItem(
                    service = service,
                    isSelected = selectedServices.contains(service.service_id),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedServices.add(service.service_id)
                        } else {
                            selectedServices.remove(service.service_id)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                coroutineScope.launch {
                    selectedWorkTimeId.value?.let { scheduleId ->
                        createAppointment(
                            patientId = patientId,
                            scheduleId = scheduleId,
                            selectedServices = selectedServices.toList(),
                            onError = onError
                        )
                    }
                }
            },
            enabled = selectedWorkTimeId.value != null && selectedServices.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tạo Appointment")
        }
    }
}

suspend fun createAppointment(
    patientId: Int,
    scheduleId: Int,
    selectedServices: List<Int>,
    onError: (String) -> Unit
) {
    try {
        val appointmentRequest = AppointmentRequest(
            status = "Pending",
            patient_id = patientId,
            schedule_id = scheduleId
        )
        val response = appointmentService.addAppoinment(appointmentRequest)
        if (response.isSuccessful) {
            val appointmentId = response.body()?.appointment_id
            if (appointmentId != null) {
                // Tạo AppointmentServices
                selectedServices.forEach { serviceId ->
                    try {
                        serviceService.addAppoinmentServices(
                            AppointmentService(
                                appointment_id = appointmentId,
                                service_id = serviceId
                            )
                        )
                        Log.d("AppointmentService", "Service $serviceId added to appointment $appointmentId")
                    } catch (e: Exception) {
                        onError("Failed to add service $serviceId: ${e.localizedMessage}")
                    }
                }
            } else {
                onError("Failed to retrieve appointment ID")
            }
        } else {
            onError("Failed to create appointment: ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        onError("Error: ${e.localizedMessage}")
    }
}




private fun formatDate(date: Date): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}
// Hàm so sánh ngày
fun isSameDay(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance().apply { time = date1 }
    val calendar2 = Calendar.getInstance().apply { time = date2 }
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
            calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
}
class LocalTimeAdapter : TypeAdapter<LocalTime>() {
    override fun write(out: JsonWriter, value: LocalTime) {
        out.value(value.toString()) // Ghi LocalTime thành chuỗi
    }

    override fun read(reader: JsonReader): LocalTime {
        return LocalTime.parse(reader.nextString()) // Đọc chuỗi và chuyển thành LocalTime
    }
}