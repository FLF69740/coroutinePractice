package com.androidnative

import android.app.Application
import com.androidnative.koinone.appModule
import com.androidnative.koinone.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewmodelModule))
        }
    }
}