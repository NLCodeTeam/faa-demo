package com.kapait.faa.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//------------------------------Переменные-------------------------------------------------------
        val v: View = inflater.inflate(R.layout.fragment_register, container, false)
        val loginFragment: Fragment = LoginFragment()
        val emailRegister: EditText = v.findViewById(R.id.emailRegister)
        val passwordRegister: EditText = v.findViewById(R.id.passwordRegister)
        val signUpBtn: Button = v.findViewById(R.id.signUpButton)
        val loginFromRegisterBtn: TextView = v.findViewById(R.id.loginFromRegister)
        auth = FirebaseAuth.getInstance()
//-----------------------------------------------------------------------------------------------

        // Реализация кнопки SIGNUPBUTTON
        signUpBtn.setOnClickListener {
            if (emailRegister.text.trim().toString().isNotEmpty() || passwordRegister.text.trim()
                    .toString().isNotEmpty()
            ) {
                createUser(
                    emailRegister.text.trim().toString(),
                    passwordRegister.text.trim().toString()
                )
            } else {
                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_LONG).show()
            }
        }

        // Реализация кнопки LOGIN
        loginFromRegisterBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit()
        }

        return v
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    context?.run {
                        email.saveEmail(this)
                        password.savePassword(this)
                    }
                    Log.e("Task Message", "Successful..")
                    val mainActivity = Intent(context, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    startActivity(mainActivity)
                } else {
                    Log.e("Task Message", "Failed " + task.exception)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if (user != null) {
            val mainActivity = Intent(context, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }

}