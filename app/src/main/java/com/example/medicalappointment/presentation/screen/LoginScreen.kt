package com.example.medicalappointment.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalappointment.data.LoginRequest
import com.example.medicalappointment.data.LoginResponse
import com.example.medicalappointment.data.RegisterRequest
import com.example.medicalappointment.data.RegisterResponse
import com.example.medicalappointment.retrofit.service.AccountService
import com.example.medicalappointment.ui.theme.BluePrimary
import com.example.medicalappointment.ui.theme.poppinsFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

@Composable
fun LoginRegisterScreen(
    accountService: AccountService,
    onUserAuthenticated: (Int) -> Unit
) {
    var isLoginScreen by remember { mutableStateOf(true) }

    if (isLoginScreen) {
        LoginScreen(
            onLogin = { username, password ->
                // Gọi API đăng nhập
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = accountService.checkLogin(LoginRequest(username, password))
                        if (response.isSuccessful) {
                            val result = response.body()
                            val userId = result?.userId
                            Log.d("LoginScreen","Result: $result")
                            Log.d("LoginScreen","User ID: $userId")
                            withContext(Dispatchers.Main) {
                                userId?.let { onUserAuthenticated(it) }
                            }
                        } else {
                            // Xử lý lỗi từ server
                            withContext(Dispatchers.Main) {
                                Log.e("LoginError", "Error: ${response.errorBody()?.string()}")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace() // Xử lý lỗi kết nối
                    }
                }
            },
            onSwitchToRegister = { isLoginScreen = false }
        )
    } else {
        RegisterScreen(
            onRegister = { username, password, email ->
                // Gọi API đăng ký
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = accountService.registerAccount(RegisterRequest(username, email, password, "patient"))
                        if (response.isSuccessful) {
                            val result = response.body()
                            val userId = result?.user_id
                            withContext(Dispatchers.Main) {
                                userId?.let { onUserAuthenticated(it) }
                            }
                        } else {
                            // Xử lý lỗi từ server
                            withContext(Dispatchers.Main) {
                                Log.e("RegisterError", "Error: ${response.errorBody()?.string()}")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace() // Xử lý lỗi kết nối
                    }
                }

            },
            onSwitchToLogin = { isLoginScreen = true }
        )
    }
}

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onSwitchToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontFamily = poppinsFontFamily,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLogin(username, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF63B4FF).copy(alpha = 0.1f)),
        ) {
            Text(
                fontFamily = poppinsFontFamily,
                color = BluePrimary,
                fontWeight = FontWeight.Medium,
                text = "Login"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onSwitchToRegister) {
            Text("Don't have an account? Register here")
        }
    }
}

@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit,
    onSwitchToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            fontFamily = poppinsFontFamily,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onRegister(username, password, email) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF63B4FF).copy(alpha = 0.1f)),
        ) {
            Text(
                fontFamily = poppinsFontFamily,
                color = BluePrimary,
                fontWeight = FontWeight.Medium,
                text = "Register"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onSwitchToLogin) {
            Text("Already have an account? Login here")
        }
    }
}
class AccountRepository(private val service: AccountService) {

    suspend fun register(username: String, email: String, password: String): Response<RegisterResponse> {
        val request = RegisterRequest(username, email, password,"patient")
        return service.registerAccount(request)
    }

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)
        return service.checkLogin(request)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewLoginRegisterScreen() {
//    LoginRegisterScreen(
//        onLogin = { username, password ->
//            println("Login: Username=$username, Password=$password")
//        },
//        onRegister = { username, password, email ->
//            println("Register: Username=$username, Password=$password, Email=$email")
//        }
//    )
//}