package com.example.allvets.presentation.login

data class AVLoginState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false

)
