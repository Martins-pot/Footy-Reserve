package com.mertswork.footyreserve.core.presentation.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
}