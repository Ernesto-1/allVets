package com.example.allvets.domain.login

import com.google.firebase.auth.FirebaseUser

interface AVLoginRepo {

    suspend fun singIn(email: String, password: String): FirebaseUser?

}