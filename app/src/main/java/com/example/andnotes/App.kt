package com.example.andnotes

import android.app.Application
import com.example.display.modules.displayModule
import com.example.display.modules.displayVMModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(displayModule, displayVMModule))
        }
    }

}