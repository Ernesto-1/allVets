package com.example.allvets.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvets.domain.login.AVLoginUseCase
import com.example.allvets.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AVLoginViewModel @Inject constructor(private val useCase: AVLoginUseCase) : ViewModel() {

    var state by mutableStateOf(AVLoginState())
        private set

    fun onEvent(event: AVLoginEvent) {

        when (event) {
            is AVLoginEvent.Login -> {
                viewModelScope.launch {
                    useCase.invoke(event.email, event.password).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(
                                    loading = true
                                )
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    isUserAutenticate = result.data,
                                    message = if (result.data == null) "No existe registro o se encuentra en validacion" else "",
                                    loading = false
                                )
                            }
                            is Resource.Failure -> {
                                state =
                                    if (result.exception.message?.contains("The password is invalid or the user does not have a password") == true || result.exception.message?.contains("There is no user record corresponding to this identifier") == true  ) {
                                        state.copy(
                                            message = "El usuario o contraseña son incorrectos",
                                        )
                                    }else{
                                        state.copy(message = "Error")
                                    }
                                state = state.copy(isUserAutenticate = null, loading = false)
                            }
                        }
                    }
                }
            }
            is AVLoginEvent.SignUp -> {

            }
        }
    }

}