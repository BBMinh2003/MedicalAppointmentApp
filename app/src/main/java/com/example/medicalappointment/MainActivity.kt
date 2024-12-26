package com.example.medicalappointment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medicalappointment.data.Patient
import com.example.medicalappointment.presentation.screen.AccountRepository
import com.example.medicalappointment.presentation.screen.LoginRegisterScreen
import com.example.medicalappointment.presentation.screen.MainScreen
import com.example.medicalappointment.retrofit.client.ApiClient
import com.example.medicalappointment.retrofit.service.AccountService
import com.example.medicalappointment.ui.theme.MedicalAppointmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicalAppointmentTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login_register"
                ) {
                    composable("login_register") {
                        LoginRegisterScreen(
                            accountService = ApiClient.accountService,
                            onUserAuthenticated = { userId ->
                                navController.navigate("main_screen/$userId") {
                                    popUpTo("login_register") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(
                        route = "main_screen/{userId}",
                        arguments = listOf(navArgument("userId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getInt("userId")
                        Log.d("MainScreen","User ID: $userId")
                        MainScreen(userId = userId ?: 0)
                    }
                }
            }
        }
    }
}