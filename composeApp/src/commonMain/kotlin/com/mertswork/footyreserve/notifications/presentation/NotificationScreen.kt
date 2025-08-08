package com.mertswork.footyreserve.notifications.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import org.koin.compose.koinInject


@Composable
fun NotificationsScreen() {
//    val imagePicker: ImagePicker = koinInject()
//    val viewModel: RegisterViewModel = koinInject()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("⚙️ Notifications")
//        RegisterScreen(imagePicker = imagePicker, viewModel = viewModel)
    }
}