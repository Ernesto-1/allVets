package com.example.allvets.domain.medical_record

import com.example.allvets.data.remote.DocumentsRecord

interface AVMedicalRecordRepo {
    suspend fun getMedicalRecord(idUser: String, idPet: String): DocumentsRecord
}