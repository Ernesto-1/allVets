package com.example.allvets.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvets.domain.home.AVHomeUseCase
import com.example.allvets.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AVHomeViewModel @Inject constructor(val useCase: AVHomeUseCase) : ViewModel() {

    var state by mutableStateOf(AVHomeState())
        private set

    fun onEvent(event: AVHomeEvent) {
        when (event) {
            is AVHomeEvent.GetMyUser -> {
                viewModelScope.launch {
                    useCase.invoke(event.idUser).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(
                                    loadingUser = true
                                )
                            }
                            is Resource.Failure -> {
                                state = state.copy(
                                    loadingUser = false
                                )
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    loadingUser = false,
                                    dataUser = result.data
                                )
                            }
                        }
                    }
                }
            }
            is AVHomeEvent.GetAllQuotes -> {
                viewModelScope.launch {
                    useCase.getAllQuotes(event.idConsult).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(loadingQuotes = true)
                            }
                            is Resource.Failure -> {
                                state = state.copy(loadingQuotes = false)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    loadingQuotes = false,
                                    dataAllQuotes = result.data
                                )
                            }
                        }
                    }
                }
            }
            is AVHomeEvent.SendDate -> {
                viewModelScope.launch {
                    useCase.sendDate(event.dataSend).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(isSendDate = false)
                            }
                            is Resource.Failure -> {
                                state = state.copy(isSendDate = false)
                            }
                            is Resource.Success -> {
                                state = state.copy(isSendDate = result.data)
                            }
                        }
                    }
                }
            }
            is AVHomeEvent.FilterQuotes -> {
                setList(event.selectTab, event.idVet)
            }
        }
    }

    private fun setList(
        tabSelect: Int,
        idVet: String
    ) {
        viewModelScope.launch {
            val allList =
                state.dataAllQuotes.filter {
                    it.status.toString() != "confirmada" && it.status.toString() != "completada"
                }
                    .toMutableList()
            val filterList =
                state.dataAllQuotes.filter {
                    it.status.toString() == "confirmada" ||
                            it.status.toString() == "completada" ||
                            it.affairs.toString() == "Emergencia"
                }.sortedWith(
                    compareBy(
                        { it.status },
                        { it.affairs }
                    )
                ).asReversed().toMutableList()



            state = when (tabSelect) {
                0 -> state.copy(dataFilterQuotes = allList)
                1 -> state.copy(dataFilterQuotes = filterList)
                else -> state.copy(dataFilterQuotes = filterList)
            }
        }
    }
}