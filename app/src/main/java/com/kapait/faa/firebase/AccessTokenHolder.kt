package com.kapait.faa.firebase

import com.kapait.faa.models.AccessToken

interface AccessTokenHolder {
    fun getAccessToken(): AccessToken?
    fun requestNewAccessToken(): AccessToken?
}