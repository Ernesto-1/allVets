package com.example.allvets.presentation.diagnosis

import com.example.allvets.data.remote.model.AVDiagnosisRequest

sealed class AVDiagnosisEvent {
    data class SaveDiagnosis(val data: AVDiagnosisRequest) : AVDiagnosisEvent()
}