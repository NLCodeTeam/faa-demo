package com.kapait.faa.firebase

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.kapait.faa.Const
import com.kapait.faa.Const.USER
import com.kapait.faa.models.AccessToken
import com.kapait.faa.models.User

class DatabaseImpl(
    private val preferences: SharedPreferences
): Database {

    private fun saveAccessToken(token: AccessToken?) {
        preferences.edit().putString(Const.accessTokenValue, token?.token ?: "").apply()
        preferences.edit().putLong(Const.accessTokenExpirationTime, token?.expirationTime ?: 0L)
            .apply()
    }

    override fun getAccessToken(): AccessToken? {
        val token = preferences.getString(Const.accessTokenValue, "") ?: ""
        val tokenExpirationTime = preferences.getLong(Const.accessTokenExpirationTime, 0L)
        if (token.isEmpty() || tokenExpirationTime == 0L)
            return null
        return AccessToken(token, tokenExpirationTime)
    }

    override fun requestNewAccessToken(): AccessToken? {
        try {
            val task = FirebaseAuth.getInstance().currentUser?.getIdToken(true) ?: return null
            while (true) {
                if (task.isComplete) {
                    val token = task.result ?: return null
                    val accessToken = AccessToken(token.token ?: "", token.expirationTimestamp)
                    saveAccessToken(accessToken)
                    return accessToken
                }
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            return null
        }
    }

    override fun saveUser(user: User) {
        saveToPrefs(USER,user.toJson())
    }

    override fun loadUser(): User? {
        val userJson = loadFromPrefs(USER)
        return if (userJson != null) {
            User.fromJson(userJson)
        } else null
    }


    private fun saveToPrefs(field: String,argument: String) {
        preferences.edit().apply{
            putString(field, argument)
            apply()
        }
    }

    private fun loadFromPrefs(field: String): String? =
        preferences.getString(field,null)
}