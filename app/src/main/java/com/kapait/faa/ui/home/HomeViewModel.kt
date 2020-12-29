package com.kapait.faa.ui.home

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val vacanciesMutableLiveData = MutableLiveData<List<Vacancy>>()

    val vacanciesLiveData: LiveData<List<Vacancy>>
        get() = vacanciesMutableLiveData

    fun loadVacancies() {
        FirebaseFirestore.getInstance()
            .collection("Vacancies")
            .get()
            .addOnSuccessListener{
                val vacancies: List<Vacancy> = it.toObjects(Vacancy::class.java)
                vacanciesMutableLiveData.value = vacancies
            }

    }
}