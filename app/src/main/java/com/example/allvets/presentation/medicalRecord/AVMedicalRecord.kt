package com.example.allvets.presentation.medicalRecord

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.allvets.data.remote.model.MedicalRecordData
import com.example.allvets.data.remote.model.RecordData

data class AVMedicalRecordState(
    val loading: Boolean = false,
    val medicalRecordData: MedicalRecordData? = null,
    val medicalRecordSelect: MutableState<RecordData> = mutableStateOf(RecordData()),
)
