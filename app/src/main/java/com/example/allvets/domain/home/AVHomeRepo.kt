package com.example.allvets.domain.home

import com.example.allvets.presentation.home.SendInfoDate
import com.google.firebase.firestore.DocumentSnapshot

interface AVHomeRepo {

    suspend fun getPatient(idConsult: String): List<DocumentSnapshot?>

    suspend fun getMyUser(idUser: String): DocumentSnapshot?

    suspend fun sendDate(dataSned: SendInfoDate): Boolean
}