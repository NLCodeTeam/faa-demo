package com.kapait.faa.ui.login

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "User"
private const val LOGIN_NAME = "Login"
private const val PASSWORD_NAME = "Password"

fun String.saveEmail(context: Context) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply{
        putString(LOGIN_NAME, this@saveEmail)
        apply()
    }
}

fun String.savePassword(context: Context) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply{
        putString(PASSWORD_NAME, this@savePassword)
        apply()
    }
}


fun Context.getEmail(): String? =
    this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(LOGIN_NAME,null)

fun Context.getPassword(): String? =
    this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(PASSWORD_NAME,null)

