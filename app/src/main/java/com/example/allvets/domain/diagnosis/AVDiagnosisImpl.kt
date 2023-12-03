package com.example.allvets.domain.diagnosis

import com.example.allvets.data.remote.diagnosis.AVDiagnosisDataSource
import com.example.allvets.data.remote.model.AVDiagnosisRequest
import javax.inject.Inject

class AVDiagnosisImpl @Inject constructor(var dataSource: AVDiagnosisDataSource) : AVDiagnosis {

    override suspend fun saveDiagnosis(diagnosis: AVDiagnosisRequest): Boolean =
        dataSource.sendDiagnosis(diagnosis)
}