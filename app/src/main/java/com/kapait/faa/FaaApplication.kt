package com.kapait.faa

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kapait.faa.di.AppComponent
import com.kapait.faa.di.ContextModule
import com.kapait.faa.di.DaggerAppComponent
import com.kapait.faa.di.DatabaseModule

class FaaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAppComponent()
        FirebaseApp.initializeApp(this)
    }

    private fun initAppComponent() {
        appComponent =
            DaggerAppComponent.builder()
            .databaseModule(DatabaseModule())
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: FaaApplication

    }
}