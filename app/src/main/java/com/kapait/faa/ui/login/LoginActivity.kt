package com.kapait.faa.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kapait.faa.FaaApplication
import com.kapait.faa.R
import com.kapait.faa.models.User

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment()).commit()
        }
    }

    fun saveUser(email: String,password: String) {
        requestTokenIfExpired()
        FaaApplication.appComponent.getDatabase().saveUser(
            User().apply {
                this.login = email
                this.password = password

            }
        )
    }

    private fun requestTokenIfExpired() {
        val database = FaaApplication.appComponent.getDatabase()
        val expirationTime = database.getAccessToken()?.expirationTime ?: 0L
        val currentTimeSeconds = System.currentTimeMillis() / 1000

        if(expirationTime < currentTimeSeconds) {
            database.requestNewAccessToken()
        }
    }
}