package com.example.allvets.data.remote.diagnosis

import com.example.allvets.data.remote.model.AVDiagnosisRequest
import com.example.allvets.utils.AppConstans.ConstantsCB.CARD_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.DATES_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.PETS_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.RECORD_COLLECTION
import com.example.allvets.utils.AppConstans.ConstantsCB.USERS_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AVDiagnosisDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun sendDiagnosis(diagnosis: AVDiagnosisRequest): Boolean {
        return try {
            val dataMedicalRecord = mapOf(
                "Asunto" to diagnosis.medicalMatter,
                "Cartilla" to diagnosis.cardMedical,
                "CedulaP" to diagnosis.license,
                "Comentarios" to diagnosis.consultation.comments,
                "Diagnostico" to diagnosis.consultation.diagnosis,
                "Fecha" to diagnosis.date,
                "Tratamiento" to diagnosis.consultation.treaments
            )

            val newMedicalRecord = firebaseFirestore
                .collection(USERS_COLLECTION).document(diagnosis.idUser)
                .collection(PETS_COLLECTION).document(diagnosis.idPet)
                .collection(RECORD_COLLECTION).document()

            newMedicalRecord.set(dataMedicalRecord)

            diagnosis.cardMedical.forEach {
                val dataCardMedical = mapOf(
                    "Proxima_aplicacion" to it.nextAplication,
                    "CedulaP" to diagnosis.license,
                    "Status" to "Vigente",
                    "Fecha_vacunacion" to it.date,
                    "Tipo" to it.type,
                    "Vacuna" to it.treatment
                )

                val newCardMedical = firebaseFirestore
                    .collection(USERS_COLLECTION).document(diagnosis.idUser)
                    .collection(PETS_COLLECTION).document(diagnosis.idPet)
                    .collection(CARD_COLLECTION).document()

                newCardMedical.set(dataCardMedical)
            }

            firebaseFirestore.collection(DATES_COLLECTION)
                .document(diagnosis.idDate)
                .update(mapOf("status" to "completada")).await()

            true
        } catch (e: Exception) {
            false
        }
    }
}