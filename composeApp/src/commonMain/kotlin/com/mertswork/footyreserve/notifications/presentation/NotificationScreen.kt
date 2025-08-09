package com.mertswork.footyreserve.notifications.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.compose.rememberNavController
import com.mertswork.footyreserve.core.presentation.screens.RegistrationScreen
import com.mertswork.footyreserve.di.utils.AppModule

import org.koin.compose.koinInject


@Composable
fun NotificationsScreen() {
//    val imagePicker: ImagePicker = koinInject()
//    val viewModel: RegisterViewModel = koinInject()


    val navController = rememberNavController()
                val registrationViewModel = remember {
                    AppModule.provideRegistrationViewModel()
                }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("⚙️ Notifications")
        RegistrationScreen(
                            registrationViewModel = registrationViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onRegistrationSuccess = {
                                navController.popBackStack()
                                // Or navigate to another screen
                            }
                        )
//        RegisterScreen(imagePicker = imagePicker, viewModel = viewModel)
    }
}