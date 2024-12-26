package com.example.medicalappointment.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.data.Patient
import com.example.medicalappointment.retrofit.client.ApiClient.patientService
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileScreen(
    userId: Int,
    onSaveSuccess: () -> Unit, // Callback khi lưu thành công
    onError: (String) -> Unit // Callback khi gặp lỗi
) {
    val coroutineScope = rememberCoroutineScope()

    // Trạng thái dữ liệu bệnh nhân
    var patient by remember { mutableStateOf<Patient?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Lấy dữ liệu bệnh nhân khi khởi tạo
    LaunchedEffect(userId) {
        Log.d("ProfileScreen", "User ID: $userId")
        try {
            isLoading = true
            patient = patientService.getPatientByUserId(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            onError("Failed to fetch patient data: ${e.localizedMessage}")
        } finally {
            isLoading = false
        }
    }

    // Hiển thị loading nếu đang tải dữ liệu
    if (isLoading) {
        Text("Loading...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        return
    }

    // Hiển thị lỗi nếu không lấy được dữ liệu
    if (patient == null) {
        Text("Failed to load patient data", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        return
    }

    // Hiển thị giao diện chỉnh sửa
    var fullName by remember { mutableStateOf(patient!!.fullName ?: "") }
    var dateOfBirthString by remember {
        mutableStateOf(
            patient!!.dateOfBirth?.let {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
            } ?: ""
        )
    }
    var gender by remember { mutableStateOf(patient!!.gender ?: "") }
    var phone by remember { mutableStateOf(patient!!.phone ?: "") }
    var address by remember { mutableStateOf(patient!!.address ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Patient Profile",
            fontFamily = poppinsFontFamily,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dateOfBirthString,
            onValueChange = { dateOfBirthString = it },
            label = { Text("Date of Birth (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val updatedPatient = patient!!.copy(
                            fullName = fullName,
                            dateOfBirth = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirthString)!!,
                            gender = gender,
                            phone = phone,
                            address = address
                        )
                        Log.d("ProfileScreen","Patient ID: ${updatedPatient.patientId}")

                        // Gọi API cập nhật
                        patientService.updatePatient(updatedPatient, updatedPatient.patientId)
                        onSaveSuccess()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onError("Failed to update patient: ${e.localizedMessage}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF63B4FF).copy(alpha = 0.1f)),
        ) {
            Text(
                fontFamily = poppinsFontFamily,
                color = BluePrimary,
                fontWeight = FontWeight.Medium,
                text = "Save"
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewPatientProfileScreen() {
//    val samplePatient = Patient(
//        patientId = 1,
//        fullName = "John Doe",
//        dateOfBirth = Date(),
//        gender = "Male",
//        phone = "123456789",
//        address = "123 Main Street",
//        userId = 1
//    )
//    ProfileScreen(patient = samplePatient) { updatedPatient ->
//        // Handle save action, e.g., update database or view model
//        println("Patient updated: $updatedPatient")
//    }
//}