package com.example.allvets.domain.home

import com.example.allvets.data.remote.home.AVHomeDataSource
import com.example.allvets.presentation.home.SendInfoDate
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class AVHomeRepoImpl @Inject constructor(var dataSource: AVHomeDataSource) : AVHomeRepo {
    override suspend fun getPatient(idConsult: String): List<DocumentSnapshot?> = dataSource.getPatients(idConsult)
    override suspend fun getMyUser(idUser: String): DocumentSnapshot? = dataSource.getMyUser(idUser)
    override suspend fun sendDate(dataSned: SendInfoDate): Boolean = dataSource.sendData(dataSned)
}