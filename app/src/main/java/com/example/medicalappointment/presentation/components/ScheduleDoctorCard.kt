package com.example.medicalappointment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.R
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.PurpleGrey
import com.example.medicalappointment.ui.theme.TextColorTitle
import com.example.medicalappointment.ui.theme.poppinsFontFamily

@Composable
fun ScheduleDoctorCard(
    modifier: Modifier = Modifier,
    doctorName: String,
    specialization: String,
    workTime: String,
    status: String,
    services: String,
    onDetailClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        tonalElevation = 0.5.dp,
        shadowElevation = 0.2.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            // Doctor Info Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.doctor_avatar),
                    contentDescription = "Doctor Avatar"
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = doctorName,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextColorTitle
                    )
                    Text(
                        text = specialization,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = PurpleGrey
                    )
                }
            }

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(1.dp)
                    .alpha(0.1f),
                color = Color.Gray
            )

            // Work Time
            Text(
                text = "Work Time: $workTime",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = TextColorTitle
            )

            // Status
            Text(
                text = "Status: $status",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (status == "Available") Color.Green else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Services
            Text(
                text = "Services: $services",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = TextColorTitle,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
//@Preview
//@Composable
//private fun ScheduleDoctorCardPreview() {
//    ScheduleDoctorCard(
//        doctorName = "Dr. John Doe",
//        specialization = "Cardiology",
//        workTime = "08:00 - 10:00",
//        status = "Available",
//        onDetailClick = {
//            // Handle detail click event
//        }
//    )
//}