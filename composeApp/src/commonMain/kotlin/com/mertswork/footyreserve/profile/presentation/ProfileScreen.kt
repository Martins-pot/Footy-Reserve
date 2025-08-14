package com.mertswork.footyreserve.profile.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.mertswork.footyreserve.core.data.model.User
import com.mertswork.footyreserve.core.domain.manager.UserManager
import com.mertswork.footyreserve.core.presentation.login.LoginScreen
import com.mertswork.footyreserve.core.presentation.login.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(navController: NavController) {
    val userManager: UserManager = koinInject()
    val currentUser by userManager.currentUser.collectAsState()
    val isLoggedIn by userManager.isLoggedIn.collectAsState()
    val loginViewModel: LoginViewModel = koinInject()

    // Initialize user manager when screen loads
    LaunchedEffect(Unit) {
        userManager.initialize()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "👤 Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoggedIn && currentUser != null) {
            UserProfileContent(
                user = currentUser!!,
                onLogout = {
                    kotlinx.coroutines.GlobalScope.launch {
                        userManager.logout()
                    }
                }
            )
        } else {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { user ->
                    // UserManager automatically updates the state
                    navController.navigate("home") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun UserProfileContent(
    user: User,
    onLogout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Image
        AsyncImage(
            model = user.image,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        // User Info
        Text(
            text = "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Country: ${user.country}",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Logout")
        }
    }
}






//@Composable
//fun ProfileScreen(navController: NavController) {
//    // Get LoginViewModel from Koin
//    val viewModel: LoginViewModel = org.koin.compose.koinInject()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxSize().padding(16.dp)
//    ) {
//        Text(
//            text = "👤 Profile",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        LoginScreen(
//            viewModel = viewModel,
//            onLoginSuccess = { user ->
//                // Navigate to authenticated profile or main screen
//                navController.navigate("profile") {
//                    popUpTo("home") { inclusive = true }
//                }
//            }
//        )
//    }
//}