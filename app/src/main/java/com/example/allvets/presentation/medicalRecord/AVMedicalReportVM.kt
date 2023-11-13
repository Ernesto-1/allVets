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

  /*  init {
        onEvent(AVMedicalRecordEvent.GetMedicalRecord("6oPu7hfDz8bRDFepHrWG"))
    }*/

    fun onEvent(event: AVMedicalRecordEvent) {
        when (event) {
            is AVMedicalRecordEvent.GetMedicalRecord -> {
                viewModelScope.launch {
                    ucGetRecord.invoke(event.idUser,event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {

                            }
                            is Resource.Failure -> {

                            }
                            is Resource.Success -> {
                                state = state.copy(
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