@file:OptIn(ExperimentalMaterial3Api::class)
package com.mertswork.footyreserve.core.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertswork.footyreserve.core.domain.model.UserRegistration
import com.mertswork.footyreserve.core.presentation.components.ProfileImagePicker
import com.mertswork.footyreserve.core.presentation.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    onNavigateBack: () -> Unit,
    onRegistrationSuccess: () -> Unit = {}
) {
    val uiState by registrationViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    var userInfo by remember { mutableStateOf(UserRegistration()) }

    // Handle success navigation
    LaunchedEffect(uiState.registeredUser) {
        if (uiState.registeredUser != null) {
            onRegistrationSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Create Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Profile Image Picker
            ProfileImagePicker(
                imageUri = userInfo.imageUri,
                onImageSelected = { uri ->
                    userInfo = userInfo.copy(imageUri = uri)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // First Name
            OutlinedTextField(
                value = userInfo.firstName,
                onValueChange = { userInfo = userInfo.copy(firstName = it) },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Last Name
            OutlinedTextField(
                value = userInfo.lastName,
                onValueChange = { userInfo = userInfo.copy(lastName = it) },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = userInfo.email,
                onValueChange = { userInfo = userInfo.copy(email = it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = userInfo.password,
                onValueChange = { userInfo = userInfo.copy(password = it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Country
            OutlinedTextField(
                value = userInfo.country,
                onValueChange = { userInfo = userInfo.copy(country = it) },
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Register Button
            Button(
                onClick = {
                    scope.launch {
                        registrationViewModel.registerUser(userInfo)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Create Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Error Message
            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            // Success Message
            uiState.successMessage?.let { message ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    color = Color.Green,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}