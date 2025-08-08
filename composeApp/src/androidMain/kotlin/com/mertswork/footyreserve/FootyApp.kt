package com.mertswork.footyreserve

import android.app.Application
import com.mertswork.footyreserve.di.initKoin
import org.koin.android.ext.koin.androidContext

class FootyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@FootyApp)
        }
    }
}