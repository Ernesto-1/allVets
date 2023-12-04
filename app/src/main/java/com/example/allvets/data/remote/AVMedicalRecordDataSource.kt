package com.example.allvets.data.remote

import com.example.allvets.utils.AppConstans.ConstantsCB.PETS_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.RECORD_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.USERS_COLLECTION
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVMedicalRecordDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getMedicalRecord(idUser: String, idPet: String): DocumentsRecord {
        val medicalRecord = firebaseFirestore
            .collection(USERS_COLLECTION)
            .document(idUser)
            .collection(PETS_COLLECTION)
            .document(idPet)
            .collection(RECORD_COLLECTION)
            .get()
            .await()

        val result = DocumentsRecord(
            medicalRecord = medicalRecord.documents
        )
        return result
    }
}

data class DocumentsRecord(
    val medicalRecord: List<DocumentSnapshot?>
)