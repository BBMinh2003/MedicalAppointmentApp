package com.example.medicalappointment.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.data.DoctorWorkTime

@Composable
fun WorkTimeItem(
    workTime: DoctorWorkTime?,
    isAvailable: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        !isAvailable -> Color.Gray
        isSelected -> Color.Blue
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(enabled = isAvailable, onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = "${workTime?.start_time ?: "N/A"} - ${workTime?.end_time ?: "N/A"}",
            modifier = Modifier.padding(16.dp),
            color = Color.White
        )
    }
}
