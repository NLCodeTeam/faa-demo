package com.kapait.faa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kapait.faa.ui.home.DetailsFragment
import com.kapait.faa.ui.home.Vacancy
import com.kapait.faa.ui.login.LoginFragment

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val vacancy = intent.getSerializableExtra(Const.VACANCY) as Vacancy?
        if (savedInstanceState == null && vacancy != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailsFragment.getFragment(vacancy))
                    .commit()
        }
    }
}