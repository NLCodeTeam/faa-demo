package com.kapait.faa.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.kapait.faa.Const
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePrefs(context: Context) = context.getSharedPreferences(Const.PREFS_NAME,MODE_PRIVATE)
}