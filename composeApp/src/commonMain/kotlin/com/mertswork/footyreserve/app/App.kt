package com.mertswork.footyreserve.app


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mertswork.footyreserve.core.domain.manager.UserManager
import com.mertswork.footyreserve.core.presentation.BackgroundBlue
import com.mertswork.footyreserve.home.presentation.main.HomeScreen
import com.mertswork.footyreserve.core.presentation.navigation.Screen
import com.mertswork.footyreserve.core.presentation.screens.MainScreen
import com.mertswork.footyreserve.core.presentation.screens.WelcomeScreen
import com.mertswork.footyreserve.di.platforModule
import com.mertswork.footyreserve.di.sharedModule
import com.mertswork.footyreserve.home.presentation.main.components.TopItem
import com.mertswork.footyreserve.notifications.presentation.NotificationsScreen
import com.mertswork.footyreserve.profile.presentation.ProfileScreen
import com.mertswork.footyreserve.ui.theme.FootyReserveTheme
import footyreserve.composeapp.generated.resources.Res
import footyreserve.composeapp.generated.resources.nav_bg
import footyreserve.composeapp.generated.resources.selected_home
import footyreserve.composeapp.generated.resources.selected_notifications
import footyreserve.composeapp.generated.resources.selected_profile
import footyreserve.composeapp.generated.resources.unselected_home
import footyreserve.composeapp.generated.resources.unselected_notifications
import footyreserve.composeapp.generated.resources.unselected_profile
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val hasNews : Boolean,
    val badgeCount : Int? = null
)


@Composable
fun App() {
    val userManager: UserManager = koinInject()
    val currentUser by userManager.currentUser.collectAsState()
    val isLoggedIn by userManager.isLoggedIn.collectAsState()

    val navController = rememberNavController()
    // Initialize user manager when app starts
    LaunchedEffect(Unit) {
        userManager.initialize()
    }


    FootyReserveTheme {
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn && currentUser != null) "main/home" else "welcome"
        ) {
            composable("welcome") {
                WelcomeScreen(
                    loginClick = { navController.navigate("main/profile") },
                    registerClick = { navController.navigate("main/notifications") }
                )
            }
            composable("main/{tab}") { backStackEntry ->
                val tab = backStackEntry.arguments?.getString("tab") ?: Screen.Home.route
                MainScreen(startDestination = tab)
            }
        }
    }
}








