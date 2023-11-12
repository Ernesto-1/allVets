package com.example.allvets.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.allvets.data.remote.model.Quotes
import com.example.allvets.data.remote.model.UserDataClass

data class AVHomeState(
    val loadingUser: Boolean = false,
    val errorUser: Boolean = false,
    var messageUser: String = "",
    var dataUser: UserDataClass? = UserDataClass(),
    val loadingQuotes: Boolean = false,
    val errorUserQuotes: Boolean = false,
    var isSendDate: Boolean? = null,
    var messageUserQuotes: String = "",
    var dataFilterQuotes: MutableList<Quotes> = mutableListOf(),
    var dataAllQuotes: List<Quotes> = emptyList(),
    val dataQuotesSelected: MutableState<Quotes> = mutableStateOf(Quotes()),
    val tabSelected: MutableState<Int> = mutableStateOf(0)


)
