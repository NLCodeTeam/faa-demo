package com.kapait.faa.ui.login


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kapait.faa.MainActivity
import com.kapait.faa.R


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//------------------------------Переменные-------------------------------------------------------
        val v: View = inflater.inflate(R.layout.fragment_login, container, false)
        val mainActivity = Intent(context, MainActivity::class.java)
        val registerFragment: Fragment = RegisterFragment()
        val forgotPasswordFragment: Fragment = ForgotPasswordFragment()
        val emailLogin: EditText = v.findViewById(R.id.emailLogin)
        val passwordLogin: EditText = v.findViewById(R.id.passwordLogin)
        val loginBtn: Button = v.findViewById(R.id.loginButton)
        val guestBtn: Button = v.findViewById(R.id.guestButton)
        val createAccount: TextView = v.findViewById(R.id.signUp)
        val forgotPassword: TextView = v.findViewById(R.id.forgotPassword)
        auth = FirebaseAuth.getInstance()
//-----------------------------------------------------------------------------------------------

        // Реализация кнопки LOGIN
        loginBtn.setOnClickListener {
            if (emailLogin.text.trim().toString().isNotEmpty() || passwordLogin.text.trim()
                    .toString().isNotEmpty()
            ) {
                signInUser(
                    emailLogin.text.trim().toString(),
                    passwordLogin.text.trim().toString()
                )
            } else {
                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_LONG).show()
            }
        }

        // Реализация кнопки GUEST
        guestBtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(mainActivity)
                    }
                }
        }

        // Реализация кнопки CREATE ACCOUNT
        createAccount.setOnClickListener {
            goToFragment(registerFragment)
        }

        // Реализация кнопки FORGOT PASSWORD?
        forgotPassword.setOnClickListener {
            goToFragment(forgotPasswordFragment)
        }


        return v
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val mainActivity = Intent(context, MainActivity::class.java)
                    startActivity(mainActivity)
                } else {
                    Toast.makeText(context, "Error" + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun goToFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commit()
    }
}