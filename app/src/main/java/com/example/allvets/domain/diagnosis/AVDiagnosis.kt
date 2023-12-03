package com.example.allvets.domain.diagnosis

import com.example.allvets.data.remote.model.AVDiagnosisRequest

interface AVDiagnosis {
    suspend fun saveDiagnosis(diagnosis: AVDiagnosisRequest): Boolean
}