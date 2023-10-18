package com.example.allvets.presentation.login

sealed class AVLoginEvent{
    data class Login(val email: String, val password: String) : AVLoginEvent()
    data class SignUp(val state: String) : AVLoginEvent()

}
