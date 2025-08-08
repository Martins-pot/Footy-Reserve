package com.mertswork.footyreserve

import androidx.compose.ui.window.ComposeUIViewController
import com.mertswork.footyreserve.app.App
import com.mertswork.footyreserve.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }