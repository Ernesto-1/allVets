package com.example.allvets.data.remote.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVLoginDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firebaseFirestore: FirebaseFirestore) {

    suspend fun logIn(email: String, password: String): FirebaseUser? {
        val existingVet = firebaseFirestore.collection("Veterinarios")
            .whereEqualTo("Correo", email)
            .whereIn("Estatus", listOf("Activo"))
            .get()
            .await()

        return if (existingVet.isEmpty) {
            Log.e("registerDate", "Usuario no encontrado")
            null
        }else{
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user
        }
    }
}