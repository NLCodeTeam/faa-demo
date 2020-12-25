package com.kapait.faa.firebase

import com.kapait.faa.models.User

interface Database : AccessTokenHolder {
    fun saveUser(user: User)
    fun loadUser(): User?
}