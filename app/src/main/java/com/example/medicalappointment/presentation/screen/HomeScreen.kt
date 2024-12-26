package com.example.medicalappointment.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.R
import com.example.medicalappointment.data.Doctor
import com.example.medicalappointment.data.Specialization
import com.example.medicalappointment.presentation.components.DoctorCard
import com.example.medicalappointment.presentation.components.SpecializationItem
import com.example.medicalappointment.presentation.components.ScheduleTimeContent
import com.example.medicalappointment.retrofit.client.ApiClient.doctorService
import com.example.medicalappointment.retrofit.client.ApiClient.specializationService
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.GraySecond
import com.example.medicalappointment.ui.theme.PurpleGrey
import com.example.medicalappointment.ui.theme.WhiteBackground
import com.example.medicalappointment.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import com.example.medicalappointment.data.Patient
import com.example.medicalappointment.retrofit.client.ApiClient

@Composable
fun HomeScreen (
    modifier: Modifier = Modifier,
    userId: Int,
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()

    // Trạng thái lưu trữ dữ liệu
    var patient by remember { mutableStateOf<Patient?>(null) }
    var doctors by remember { mutableStateOf<List<Doctor>>(emptyList()) }
    var specializations by remember { mutableStateOf<List<Specialization>>(emptyList()) }
    var specializationMap by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var selectedSpecializationId by remember { mutableStateOf<Int?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                isLoading = true
                val doctorsList = doctorService.getAllDoctors()
                val specializationList = specializationService.getAllSpecialization()
                doctors = doctorsList
                specializations = specializationList
                patient = ApiClient.patientService.getPatientByUserId(userId)

                specializationMap = specializationList.associate { it.specializationId to it.name }
            } catch (e: Exception) {
                e.printStackTrace() // Hiển thị lỗi hoặc thông báo cho người dùng
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Text("Loading...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        return
    }
    val filteredDoctors = if (selectedSpecializationId != null) {
        doctors.filter { it.specializationId == selectedSpecializationId }
    } else {
        doctors
    }
    Surface (
        modifier = modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ){
        Column{
            //Header Content
            HeaderContent(name = patient?.fullName)

            Spacer(modifier = Modifier.height(32.dp))
            ScheduleContent()
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                value = "",
                placeholder ={
                    Text(
                        text = "Search doctor",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.W400,
                        color = PurpleGrey
                    )
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Icon Search"
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = WhiteBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                onValueChange = {

                }
            )

            //Specialization List
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(specializations) { specialization ->
                    SpecializationItem(
                        specialization = specialization,
                        isSelected = specialization.specializationId == selectedSpecializationId,
                        onClick = {selectedSpecializationId = specialization.specializationId }
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Danh sach bac si:",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            //Doctor List
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredDoctors) { doctor ->
                    patient?.let {
                        DoctorCard(
                            patientId = it.patientId,
                            doctor = doctor,
                            specializationName = specializationMap[doctor.specializationId] ?: "Unknown",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderContent(modifier: Modifier = Modifier,name: String?){
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column {
            Text(
                text = "Hello",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.W400,
                color = PurpleGrey
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Hi $name",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.user_avatar),
            contentDescription = "User avatar"
        )
    }
}

@Composable
private fun ScheduleContent(modifier: Modifier = Modifier){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = BluePrimary,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Column (
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.doctor_avatar),
                    contentDescription = "Avatar Doctor"
                )

                Column (
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ){
                    Text(
                        text = "Dr. A",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "General Doctor",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Light,
                        color = GraySecond
                    )
                }

                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow Next",
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(1.dp)
                    .alpha(0.2f),
                color = Color.White
            )
            ScheduleTimeContent()
    }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HomeScreenPreView(){
//    HomeScreen()
//}