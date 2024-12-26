package com.example.medicalappointment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicalappointment.R
import com.example.medicalappointment.data.Specialization
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.WhiteBackground
import com.example.medicalappointment.ui.theme.poppinsFontFamily

@Composable
fun SpecializationItem(
    modifier: Modifier = Modifier,
    specialization: Specialization,
    isSelected: Boolean, // Trạng thái được chọn
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(
                color = if (isSelected) BluePrimary else WhiteBackground, // Đổi màu khi được chọn
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.circle_cross),
                contentDescription = "Icon"
            )
            Text(
                text = specialization.name,
                fontFamily = poppinsFontFamily,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.W400, // Đổi font weight khi chọn
                color = if (isSelected) Color.White else Color.Black // Đổi màu chữ khi được chọn
            )
        }
    }
}


//@Preview
//@Composable
//fun SpecializationItemPreview(){
//    SpecializationItem(title = "Khoa tim mach", onClick = {})
//}