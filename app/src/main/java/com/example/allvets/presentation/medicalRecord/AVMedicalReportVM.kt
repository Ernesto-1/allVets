package com.example.allvets.presentation.medicalRecord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvets.domain.medical_record.GetMedicalRecordUseCase
import com.example.allvets.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AVMedicalRecordVM @Inject constructor(val ucGetRecord: GetMedicalRecordUseCase) :
    ViewModel() {

    var state by mutableStateOf(AVMedicalRecordState())
        private set

    fun onEvent(event: AVMedicalRecordEvent) {
        when (event) {
            is AVMedicalRecordEvent.GetMedicalRecord -> {
                viewModelScope.launch {
                    ucGetRecord.invoke(event.idUser,event.idPet).collect() { result ->
                        state = when (result) {
                            is Resource.Loading -> {
                                state.copy(loading = true)
                            }
                            is Resource.Failure -> {
                                state.copy(loading = false)
                            }
                            is Resource.Success -> {
                                state.copy(
                                    loading = false,
                                    medicalRecordData = result.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}