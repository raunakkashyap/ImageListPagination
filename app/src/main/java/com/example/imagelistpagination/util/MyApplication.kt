package com.example.imagelistpagination.util

import android.app.Application
import com.example.imagelistpagination.module.imageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(imageModule)
        }
    }
}
