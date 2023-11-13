package com.example.allvets.presentation.medicalRecord

sealed class AVMedicalRecordEvent {

    data class GetMedicalRecord(val idUser: String, val idPet: String) : AVMedicalRecordEvent()
}