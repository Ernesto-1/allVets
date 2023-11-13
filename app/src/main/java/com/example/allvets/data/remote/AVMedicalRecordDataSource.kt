package com.example.allvets.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVMedicalRecordDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun getMedicalRecord(idUser: String, idPet: String): DocumentsRecord {
        val data = firebaseFirestore
            .collection("Users")
            .document(idUser)
            .collection("Mascotas")
            .whereEqualTo("id", idPet)
            .get()
            .await()

        val medicalRecord = firebaseFirestore
            .collection("Users")
            .document(idUser)
            .collection("Mascotas")
            .document(idPet)
            .collection("Expediente")
            .get()
            .await()

        val result = DocumentsRecord(
            data = data.documents,
            medicalRecord = medicalRecord.documents
        )
        return result
    }
}

data class DocumentsRecord(
    val data: List<DocumentSnapshot?>,
    val medicalRecord: List<DocumentSnapshot?>
)