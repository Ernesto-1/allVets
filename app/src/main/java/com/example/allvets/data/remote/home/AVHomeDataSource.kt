package com.example.allvets.data.remote.home

import com.example.allvets.presentation.home.SendInfoDate
import com.example.allvets.utils.AppConstans.ConstantsCB.DATES_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.VETS_COLLECTION
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVHomeDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getPatients(idConsult: String): List<DocumentSnapshot?> {
        val userPetsCollection =
            firebaseFirestore
                .collection(DATES_COLLECTION)
                .whereEqualTo("idConsultorio", idConsult)
        return try {
            val getDates = userPetsCollection.get().await()
            getDates.documents
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getMyUser(idUser: String): DocumentSnapshot? {
        return firebaseFirestore
            .collection(VETS_COLLECTION)
            .document(idUser).get()
            .await()
    }

    suspend fun sendData(dataSned: SendInfoDate): Boolean {
        return try {
            val updateDataDate = if (dataSned.isConfirmed) {
                mapOf(
                    "status" to dataSned.status,
                    "idVet" to dataSned.idVet,
                    "citaSolicitada" to dataSned.requestedAppointment
                )
            } else {
                mapOf("idVet" to dataSned.idVet, "status" to dataSned.status)
            }
            firebaseFirestore.collection(DATES_COLLECTION)
                .document(dataSned.idDate)
                .update(updateDataDate).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}