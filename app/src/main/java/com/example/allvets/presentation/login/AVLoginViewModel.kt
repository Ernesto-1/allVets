package com.example.allvets.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvets.domain.login.AVLoginUseCase
import com.example.allvets.utils.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AVLoginViewModel @Inject constructor(private val useCase: AVLoginUseCase) : ViewModel() {

    private val auth = Firebase.auth

    var state by mutableStateOf(AVLoginState())
        private set

    fun onEvent(event: AVLoginEvent) {

        when (event) {
            is AVLoginEvent.Login -> {
                viewModelScope.launch {
                    useCase.invoke(event.email, event.password).collect { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(
                                    success = true
                                )
                                Log.d("vgbhnjmk", result.data?.uid.toString())
                            }
                            is Resource.Failure -> {
                                Log.d("rftyghuj", result.exception.message.toString())
                                state =
                                    if (result.exception.message?.contains("The password is invalid or the user does not have a password") == true || result.exception.message?.contains("There is no user record corresponding to this identifier") == true  ) {
                                        state.copy(
                                            message = "El usuario o contraseña son incorrectos",
                                        )
                                    } else if (result.exception.message?.contains("There is no user record corresponding to this identifier") == true) {
                                        state.copy(message = "No existe una cuenta asociada a este correo electronico")
                                    }else{
                                        state.copy(message = "Error")
                                    }
                                state = state.copy(success = false)

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