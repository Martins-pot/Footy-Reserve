package com.mertswork.footyreserve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mertswork.footyreserve.app.App
import com.mertswork.footyreserve.core.presentation.BackgroundBlue
import com.mertswork.footyreserve.core.presentation.navigation.Screen
import com.mertswork.footyreserve.core.presentation.screens.RegistrationScreen
import com.mertswork.footyreserve.di.utils.AppModule
import com.mertswork.footyreserve.home.presentation.main.HomeScreen


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                BackgroundBlue.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.Transparent.toArgb()
            )
        )
        setContent {
            App()
        }

//                val navController = rememberNavController()
//                val registrationViewModel = remember {
//                    AppModule.provideRegistrationViewModel()
//                }
//
//                NavHost(
//                    navController = navController,
//                    startDestination = Screen.Home.route
//                ) {
//                    composable(Screen.Home.route) {
//                        HomeScreen(
//                            onNavigateTo = {
//                                navController.navigate(Screen.Notifications.route)
//                            }
//                        )
//                    }
//
//                    composable(Screen.Notifications.route) {
//                        RegistrationScreen(
//                            registrationViewModel = registrationViewModel,
//                            onNavigateBack = {
//                                navController.popBackStack()
//                            },
//                            onRegistrationSuccess = {
//                                navController.popBackStack()
//                                // Or navigate to another screen
//                            }
//                        )
//                    }
//                }
//            }
//        }
    }

    

}

