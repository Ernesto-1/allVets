package com.example.allvets.data.remote.model

import com.google.firebase.Timestamp

data class ListCardMedicalDiagnosis(
    val listCardMedical: List<CardMedicalDiagnosis>? = null
)

data class CardMedicalDiagnosis(
    val type: String,
    val treatment: String,
    val code: String,
    val nextAplication: String,
    val date: Timestamp? = null
)