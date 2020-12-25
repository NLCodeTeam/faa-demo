package com.kapait.faa.models

import android.util.Log
import com.google.gson.Gson
import com.kapait.faa.FaaApplication
import java.io.Serializable

class User: Serializable {
    var login: String? = null
    var password: String? = null
    var fullName: String? = null
    var userEmail: String? = null
    var dob: String? = null
    var country: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var bodyType: Int = 0
    var hairColor: Int = 0
    var eyeColor: Int = 0
    var selectedSkills: List<String> = ArrayList()

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object{
        @JvmStatic
        fun fromJson(json: String): User? {
            var user: User? = null
            try {
               user =  Gson().fromJson(json,User::class.java)
            } catch(e: Exception){
                Log.e(FaaApplication.toString(),e.localizedMessage)
            }
            return user
        }
    }

}

