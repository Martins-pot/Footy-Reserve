package com.mertswork.footyreserve

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mertswork.footyreserve.di.initKoin
import com.mertswork.footyreserve.app.App


fun main() =
    initKoin()
    application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FootyReserve",
    ) {
        App()
    }
}