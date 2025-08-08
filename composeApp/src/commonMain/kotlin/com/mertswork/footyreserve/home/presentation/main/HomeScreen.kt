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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.mertswork.footyreserve.core.presentation.BackgroundBlue
import com.mertswork.footyreserve.home.presentation.main.components.TopItem
import com.mertswork.footyreserve.ui.theme.Dimens

@Composable
fun HomeScreenRoot(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Dimens.HorizontalPadding
            )

    ) {
        TopItem(
            name = "Martins",
            onSearchClick = {},
            imageUrl = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d"
        )
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


