package com.example.allvets.domain.login

import com.example.allvets.data.remote.login.AVLoginDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AVLoginRepoImpl @Inject constructor(private val dataSource: AVLoginDataSource): AVLoginRepo {
    override suspend fun singIn(email: String, password: String): FirebaseUser? = dataSource.logIn(email = email, password = password)
}