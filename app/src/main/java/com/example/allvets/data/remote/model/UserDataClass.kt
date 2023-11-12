package com.example.allvets.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot

data class UserDataClass(
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val consult: String = "",
    val cedProf: String = "",
    val status: String = ""
)

fun DocumentSnapshot?.mapTouserDataClass(): UserDataClass {
    if (this == null || !exists()) {
        return UserDataClass()
    }

    val username = getString("Nombre") ?: ""
    val lastname = getString("Apellidos") ?: ""
    val email = getString("Correo") ?: ""
    val consult = getString("Consultorio") ?: ""
    val cedProf = getString("CedProf") ?: ""
    val status = getString("Estatus") ?: ""

    return UserDataClass(username, lastname,email,consult,cedProf,status)
}

