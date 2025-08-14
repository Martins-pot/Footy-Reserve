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
import com.mertswork.footyreserve.di.platforModule
import com.mertswork.footyreserve.di.sharedModule
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

    // Initialize user manager when app starts
    LaunchedEffect(Unit) {
        userManager.initialize()
    }

    FootyReserveTheme {


            val navController = rememberNavController()
            val items = listOf(
                BottomNavigationItem(
                    title = "Home",
                    selectedIcon = painterResource(Res.drawable.selected_home),
                    unselectedIcon = painterResource(Res.drawable.unselected_home),
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "Notifications",
                    selectedIcon = painterResource(Res.drawable.selected_notifications),
                    unselectedIcon = painterResource(Res.drawable.unselected_notifications),
                    hasNews = false,
                    badgeCount = 15
                ),
                BottomNavigationItem(
                    title = "Profile",
                    selectedIcon = painterResource(Res.drawable.selected_profile),
                    unselectedIcon = painterResource(Res.drawable.unselected_profile),
                    hasNews = true,
                )
            )
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundBlue)
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = BackgroundBlue,

                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp) // Set to your desired nav bar height
                        ) {
                            // Background Image
                            Image(
                                painter = painterResource(Res.drawable.nav_bg), // Use resource for KMP
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
//                        .clip(RoundedCornerShape(topStart = 44.dp, topEnd = 44.dp))
                                ,
                                contentScale = ContentScale.FillWidth
                            )

                            // Navigation Bar Items on top of the image
                            NavigationBar(
                                containerColor = Color.Transparent,
                                tonalElevation = 0.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(topStart = 44.dp, topEnd = 44.dp))
                            ) {
                                items.forEachIndexed { index, item ->
                                    val isSelected =
                                        navController.currentDestination?.route == item.title.lowercase()

                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                            selectedItemIndex = index
//                            navController.navigate(item.title)
                                            navController.navigate(item.title.lowercase()) {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                            }
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Image(
                                                    painter = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title,
                                                    modifier = Modifier
                                                        .sizeIn(60.dp, 60.dp, 70.dp, 70.dp)
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier
                            .padding(paddingValues),

                        ) {
                        composable(Screen.Home.route) { HomeScreen() }
                        composable(Screen.Notifications.route) { NotificationsScreen() }
                        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
                    }

                }
            }

        }
    }









