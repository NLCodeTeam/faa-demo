package com.kapait.faa.ui.login

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
import com.kapait.faa.R


class ForgotPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//------------------------------Переменные----------------------------------------------------------
        val v: View = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val emailResetPass: EditText = v.findViewById(R.id.emailResetPass)
        val resetButton: Button = v.findViewById(R.id.resetButton)
        val loginFromReset: TextView = v.findViewById(R.id.loginFromReset)
//--------------------------------------------------------------------------------------------------
        // Реализация кнопки RESET PASSWORD
        resetButton.setOnClickListener{
            if (emailResetPass.text.isNotEmpty()) {
                auth.sendPasswordResetEmail(emailResetPass.text.trim().toString())
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            goToLoginFragment()
                            Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Invalid email", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Enter email", Toast.LENGTH_LONG).show()
            }
        }

        // Реализация кнопки LOGIN
        loginFromReset.setOnClickListener{
            goToLoginFragment()
        }

        return v
    }

    private fun goToLoginFragment() {
        val loginFragment: Fragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()
    }

}