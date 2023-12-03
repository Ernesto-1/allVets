package com.example.allvets.data.remote.model

import com.google.firebase.Timestamp

data class AVDiagnosisRequest(
    val idUser: String,
    val idPet: String,
    val medicalMatter: String,
    val license: String,
    val cardMedical: List<CardMedicalDiagnosis>,
    val consultation: MedicalConsultation,
    val date: Timestamp? = null,
    val idDate: String
)

data class MedicalConsultation(
    val comments: String? = "",
    val diagnosis: String? = "",
    val treaments: List<String>? = listOf()
)