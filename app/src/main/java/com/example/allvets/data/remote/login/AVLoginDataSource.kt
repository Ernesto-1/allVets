package com.example.allvets.data.remote.login

import com.example.allvets.utils.AppConstans.ConstantsCB.VETS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVLoginDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun logIn(email: String, password: String): FirebaseUser? {
        val existingVet = firebaseFirestore
            .collection(VETS_COLLECTION)
            .whereEqualTo("Correo", email)
            .whereIn("Estatus", listOf("Activo"))
            .get()
            .await()

        return if (existingVet.isEmpty) {
            null
        } else {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user
        }
    }
}