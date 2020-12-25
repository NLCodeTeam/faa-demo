package com.kapait.faa.di

import com.kapait.faa.firebase.Database
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DatabaseModule::class])
interface AppComponent {
    fun getDatabase(): Database
}