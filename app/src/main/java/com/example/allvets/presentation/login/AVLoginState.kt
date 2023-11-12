package com.example.allvets.presentation.login

import com.google.firebase.auth.FirebaseUser

data class AVLoginState(
    val loading: Boolean = false,
    val error: Boolean = false,
    var message: String = "",
    var isUserAutenticate: FirebaseUser? = null

)
