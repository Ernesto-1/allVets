package com.example.allvets.presentation.diagnosis

import androidx.compose.runtime.MutableState
import com.example.allvets.data.remote.model.ListCardMedicalDiagnosis

data class AVDiagnosisState(
    val loading: Boolean = false,
    val saveSuccessfully: Boolean = false,
    val cardMedicalDiagnosis: MutableState<ListCardMedicalDiagnosis>? = null
)