package com.example.allvets.presentation.home

import android.util.Log
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
                                Log.d("UserLoading", "Loading")
                            }
                            is Resource.Failure -> {
                                Log.d("UserFailure", result.exception.message.toString())
                            }
                            is Resource.Success -> {
                                Log.d("UserSuccess", result.data.toString())
                                state = state.copy(dataUser = result.data)
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
                                Log.d("AllQuotesLoading1", "Loading")
                            }
                            is Resource.Failure -> {
                                Log.d("AllQuotesFailure1", result.exception.message.toString())
                            }
                            is Resource.Success -> {
                                state = state.copy(dataAllQuotes = result.data)
                                Log.d("AllQuotesSuccess1", result.data.toString())
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
                                Log.d("AllQuotesSuccess2", result.data.toString())
                            }
                        }
                    }
                }
            }
            is AVHomeEvent.FilterQuotes -> {
                setList(event.selectTab, event.idVet)
            }
            else -> {}
        }
    }

    private fun setList(
        tabSelect: Int,
        idVet: String
    ) {
        viewModelScope.launch {
            val allList =
                state.dataAllQuotes.filter { it.status.toString() != "confirmada" }.toMutableList()
            val filterList =
                state.dataAllQuotes.filter { it.status.toString() == "confirmada" || it.affairs.toString() == "Emergencia" }
                    .toMutableList()
            state = when (tabSelect) {
                0 -> state.copy(dataFilterQuotes = allList)
                1 -> state.copy(dataFilterQuotes = filterList)
                else -> state.copy(dataFilterQuotes = filterList)
            }
        }
    }
}