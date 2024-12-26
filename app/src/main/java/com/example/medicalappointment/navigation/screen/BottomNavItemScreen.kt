package com.example.medicalappointment.navigation.screen

import com.example.medicalappointment.R

sealed class BottomNavItemScreen(val route: String, val icon: Int, val title: String) {

    data object Home : BottomNavItemScreen(
        route = "home_screen",
        icon = R.drawable.ic_bottom_home,
        title = "Home"
    )

    data object Schedule : BottomNavItemScreen(
        route = "schedule_screen",
        icon = R.drawable.ic_bottom_schedule,
        title = "Schedule"
    )

    data object Profile : BottomNavItemScreen(
        route = "profile_screen",
        icon = R.drawable.ic_bottom_profile,
        title = "Profile"
    )

}