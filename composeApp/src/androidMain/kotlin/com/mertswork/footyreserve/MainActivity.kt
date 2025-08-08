package com.mertswork.footyreserve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mertswork.footyreserve.app.App
import com.mertswork.footyreserve.core.presentation.BackgroundBlue


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
    }

    

}

