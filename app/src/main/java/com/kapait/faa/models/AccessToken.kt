package com.kapait.faa.models

import java.io.Serializable

data class AccessToken(
    val token: String,
    val expirationTime: Long
): Serializable