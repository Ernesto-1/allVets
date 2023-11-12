package com.example.allvets.presentation.home

import com.google.firebase.Timestamp

sealed class AVHomeEvent{

    data class GetMyUser(val idUser: String): AVHomeEvent()

    data class GetAllQuotes(val idConsult: String) : AVHomeEvent()

    data class SendDate(val dataSend: SendInfoDate) : AVHomeEvent()

    data class FilterQuotes(val selectTab: Int, val idVet: String) : AVHomeEvent()

}

data class SendInfoDate(
    val idVet: String = "",
    val idDate: String = "",
    val isConfirmed: Boolean = false,
    val status: String = "",
    val requestedAppointment: Timestamp? = null
)