package com.kapait.faa.di

import android.content.SharedPreferences
import com.kapait.faa.firebase.Database
import com.kapait.faa.firebase.DatabaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(sharedPreferences: SharedPreferences): Database = DatabaseImpl(sharedPreferences)
}