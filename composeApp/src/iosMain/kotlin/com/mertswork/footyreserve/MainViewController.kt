package com.mertswork.footyreserve

import androidx.compose.ui.window.ComposeUIViewController
import com.mertswork.footyreserve.app.App
import com.mertswork.footyreserve.di.initKoin
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.mertswork.footyreserve.core.presentation.screens.RegistrationScreen
import com.mertswork.footyreserve.di.utils.AppModule
import com.mertswork.footyreserve.home.presentation.main.HomeScreen
import com.mertswork.footyreserve.ui.theme.FootyReserveTheme
import platform.UIKit.UIViewController

// Navigation for iOS - you can use a simple state-based navigation or a library
enum class IOSScreen {
    Home, Registration
}

fun MainViewController(): UIViewController = ComposeUIViewController {
    FootyReserveTheme {
        var currentScreen by remember { mutableStateOf(IOSScreen.Home) }
        val registrationViewModel = remember {
            AppModule.provideRegistrationViewModel()
        }

        when (currentScreen) {
            IOSScreen.Home -> {
                HomeScreen(
                    onNavigateToRegistration = {
                        currentScreen = IOSScreen.Registration
                    }
                )
            }

            IOSScreen.Registration -> {
                RegistrationScreen(
                    registrationViewModel = registrationViewModel,
                    onNavigateBack = {
                        currentScreen = IOSScreen.Home
                    },
                    onRegistrationSuccess = {
                        currentScreen = IOSScreen.Home
                        // Or navigate to another screen
                    }
                )
            }
        }
    }
}

// For iOS Info.plist, add these entries:
/*
<key>NSPhotoLibraryUsageDescription</key>
<string>This app needs access to your photo library to select a profile picture.</string>
<key>NSCameraUsageDescription</key>
<string>This app needs access to your camera to take a profile picture.</string>
*/
