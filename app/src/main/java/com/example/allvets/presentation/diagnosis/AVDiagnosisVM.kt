package com.example.allvets.presentation.diagnosis

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvets.data.remote.model.CardMedicalDiagnosis
import com.example.allvets.data.remote.model.MedicalConsultation
import com.example.allvets.domain.diagnosis.SaveDiagnosisUseCase
import com.example.allvets.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AVDiagnosisVM @Inject constructor(val ucSaveDiagnosis: SaveDiagnosisUseCase) :
    ViewModel() {

    var state by mutableStateOf(AVDiagnosisState())
        private set

    private val _itemList = mutableStateListOf<CardMedicalDiagnosis>()
    val itemList: List<CardMedicalDiagnosis> = _itemList

    val _diagnosis = mutableStateOf(MedicalConsultation())
    val diagnosis: MutableState<MedicalConsultation> = _diagnosis


    fun addItemCard(data: CardMedicalDiagnosis) {
        viewModelScope.launch {
            _itemList.add(data)
        }
    }

    fun saveTempDiagnosis(diagnosis: MedicalConsultation) {
        _diagnosis.value = diagnosis
    }

    fun onEvent(event: AVDiagnosisEvent) {
        when (event) {
            is AVDiagnosisEvent.SaveDiagnosis -> {
                viewModelScope.launch {
                    ucSaveDiagnosis.invoke(event.data).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(loading = true)
                            }
                            is Resource.Failure -> {
                                state = state.copy(loading = false)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    loading = false,
                                    saveSuccessfully = result.data)
                            }
                        }
                    }
                }
            }
        }
    }

}

