package com.example.allvets.domain.login

import com.example.allvets.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AVLoginUseCase @Inject constructor(private val repository: AVLoginRepo){

    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.singIn(email = email,password = password)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}