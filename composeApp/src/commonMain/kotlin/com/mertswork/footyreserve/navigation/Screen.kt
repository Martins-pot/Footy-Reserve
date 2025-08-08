package com.mertswork.footyreserve.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
}