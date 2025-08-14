package com.mertswork.footyreserve.home.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.mertswork.footyreserve.core.domain.manager.UserManager
import com.mertswork.footyreserve.core.presentation.BackgroundBlue
import com.mertswork.footyreserve.home.presentation.main.components.TopItem
import com.mertswork.footyreserve.ui.theme.Dimens
import org.koin.compose.koinInject

@Composable
fun HomeScreenRoot(){

    val userManager: UserManager = koinInject()
    val currentUser by userManager.currentUser.collectAsState()
    val isLoggedIn by userManager.isLoggedIn.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Dimens.HorizontalPadding
            )

    ) {
        if (isLoggedIn && currentUser != null) {
        TopItem(
            name = currentUser!!.firstName,
            onSearchClick = {},
            imageUrl = currentUser!!.image
            //"https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d"
        )
    } else {
            Text("Please log in")
        }
    }
}

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
    ){
        HomeScreenRoot()
    }
}


