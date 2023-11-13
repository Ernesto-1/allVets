package com.example.allvets.domain.medical_record

import com.example.allvets.data.remote.AVMedicalRecordDataSource
import com.example.allvets.data.remote.DocumentsRecord
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class AVMedicalRecordImpl @Inject constructor(var dataSource: AVMedicalRecordDataSource) :
    AVMedicalRecordRepo {

    override suspend fun getMedicalRecord(idUser: String, idPet: String): DocumentsRecord =
        dataSource.getMedicalRecord(idUser,idPet)
}