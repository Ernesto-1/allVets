package com.example.allvets.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class AllQuotesDataClass(
    val allQuotes: MutableList<Quotes> = mutableListOf()
)

data class Quotes(
    val requestedAppointment: Timestamp? = null,
    val idVet: String? = "",
    val idConsult: String? = "",
    val patient: String? = "",
    val reason: String? = "",
    val status: String? = "",
    val userId: String? = "",
    val affairs: String? = "",
    val age: String? = "",
    val id: String? = "",
    val pet: String? = "",
    val idPatient: String? = ""
)

fun List<DocumentSnapshot?>.mapToAllQuotesDataClass(): AllQuotesDataClass {
    val petDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.petData()
    }
    return AllQuotesDataClass(petDataList.toMutableList())
}

fun DocumentSnapshot?.petData(): Quotes? {
    if (this == null || !exists()) {
        return null
    }
    val requestedAppointment = getTimestamp("citaSolicitada")
    val idVet = getString("idVet") ?: ""
    val id = this.reference.id
    val idConsult = getString("idConsultorio") ?: ""
    val patient = getString("patient") ?: ""
    val idPatient = getString("idPatient") ?: ""
    val reason = getString("motivo") ?: ""
    val affairs = getString("asunto") ?: ""
    val status = getString("status") ?: ""
    val userId = getString("userId") ?: ""
    val age = getString("edad") ?: ""
    val pet = getString("mascota") ?: ""


    return Quotes(
        requestedAppointment = requestedAppointment,
        idVet = idVet,
        idConsult = idConsult,
        patient = patient,
        reason = reason,
        status = status,
        userId = userId,
        affairs = affairs,
        age =age,
        id = id,
        pet = pet,
        idPatient = idPatient
    )
}