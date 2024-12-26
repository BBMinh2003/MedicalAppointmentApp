package com.example.medicalappointment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalappointment.R
import com.example.medicalappointment.data.Doctor
import com.example.medicalappointment.ui.theme.Orange
import com.example.medicalappointment.ui.theme.PurpleGrey
import com.example.medicalappointment.ui.theme.TextColorTitle
import com.example.medicalappointment.ui.theme.poppinsFontFamily

@Composable
fun DoctorCard(
    modifier: Modifier = Modifier,
    patientId : Int,
    doctor: Doctor,
    specializationName: String,
    navController: NavController
){
    Surface(
        modifier = Modifier.fillMaxWidth()
            .clickable {
            navController.navigate("doctor_schedule/${doctor.doctorId}/${patientId}")
        },
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        tonalElevation = 0.5.dp,
        shadowElevation = 0.2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Row{
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.doctor_avatar),
                    contentDescription = "Avatar Doctor"
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = doctor.fullName,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = TextColorTitle
                    )
                    Text(
                        text = specializationName,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Light,
                        color = PurpleGrey
                    )
                }

            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(1.dp)
                    .alpha(0.1f),
                color = Color.Gray
            )

            BottomItem(
                title = doctor.yearsOfExperience,
                color = Orange
            )
        }
    }
}

@Composable
fun BottomItem(modifier: Modifier = Modifier, title: Int,color: Color) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Kinh nghiem: $title nam",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.W400,
            color = color,
            fontSize = 12.sp
        )
    }
}

//@Preview
//@Composable
//fun DoctorCardPreview(){
//    DoctorCard()
//}