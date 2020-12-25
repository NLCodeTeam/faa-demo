package com.kapait.faa.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kapait.faa.FaaApplication
import com.kapait.faa.models.User

class ProfileViewModel : ViewModel() {

    private val userMutableLiveData = MutableLiveData<User>()
    private val errorMutableLiveData = MutableLiveData<Unit>()
    private var skills = ArrayList<String>()

    val loadUserLiveData: LiveData<User>
        get() = userMutableLiveData

    val errorLiveData: LiveData<Unit>
        get() = errorMutableLiveData


    fun loadProfile() {
        val user = FaaApplication.appComponent.getDatabase().loadUser()
        if (user != null) {
            userMutableLiveData.value = user
            skills = user.selectedSkills as ArrayList<String>
        }
        else
            errorMutableLiveData.value = Unit
    }

    fun addSelectedSkill(position: String) {
        if (skills.contains(position))
            skills.remove(position)
        else
            skills.add(position)
    }

    fun updateUserInfo(user: User) {
        loadUserLiveData.value?.apply {
            fullName = user.fullName
            userEmail = user.userEmail
            dob = user.dob
            country = user.country
            height = user.height
            weight = user.weight
            bodyType = user.bodyType
            hairColor = user.hairColor
            eyeColor = user.eyeColor
            selectedSkills = skills
        }
        FaaApplication.appComponent.getDatabase().saveUser(loadUserLiveData.value!!)
    }

    fun getSelectedSkills(): List<String> = skills
}