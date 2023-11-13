package com.example.allvets.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class MedicalRecordData(
    val patient: List<PatientMedicalRecord> = listOf(),
    val vaccination: List<VaccinationData> = listOf(),
    val record: List<RecordData> = listOf()
)

fun List<DocumentSnapshot?>.toDataPatient(): MedicalRecordData {
    val data = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToDataPatient()
    }
    return MedicalRecordData(
        patient = data
    )
}

fun DocumentSnapshot?.mapToDataPatient(): PatientMedicalRecord {
    if (this == null || !exists()) {
        return PatientMedicalRecord(
            ""
        )
    }
    val name = getString("Nombre") ?: ""
    return PatientMedicalRecord(
        name
    )
}

data class VaccinationData(
    val expired: Timestamp? = null,
    val licence: String = "",
    val status: String = "",
    val date_vaccinated: Timestamp? = null,
    val type: String = "",
    val vaccine: String = ""
)

fun List<DocumentSnapshot?>.toDataVaccine(): MedicalRecordData {
    val data = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToVaccinationData()
    }
    return MedicalRecordData(
        vaccination = data
    )
}

fun DocumentSnapshot?.mapToVaccinationData(): VaccinationData {
    if (this == null || !exists()) {
        return VaccinationData()
    }

    val expire = getTimestamp("Caducidad_vacuna")
    val licence = getString("CedulaP") ?: ""
    val status = getString("Estatus") ?: ""
    val date_expiration = getTimestamp("Fecha_vacunacion")
    val type = getString("Tipo") ?: ""
    val vaccine = getString("Vacuna") ?: ""

    return VaccinationData(
        expire, licence, status, date_expiration, type, vaccine
    )
}

data class RecordData(
    val medicalMatter: String = "",
    val license: String = "",
    val comments: String = "",
    val diagnosis: String = "",
    val date: Timestamp? = null,
    val treatment: ArrayList<*>? = null,
    val medicalRecordList: ArrayList<Medicamento> = arrayListOf()
)

fun List<DocumentSnapshot?>.toDataMedicalReport(): MedicalRecordData {
    val data = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToMedicalRecordData()
    }
    return MedicalRecordData(
        record = data
    )
}

fun DocumentSnapshot?.mapToMedicalRecordData(): RecordData {
    if (this == null || !exists()) {
        return RecordData()
    }

    val medicalMatter = getString("Asunto") ?: ""
    val licence = getString("CedulaP") ?: ""
    val comments = getString("Comentarios") ?: ""
    val diagnosis = getString("Diagnostico") ?: ""
    val date = getTimestamp("Fecha")
    val treatment: ArrayList<*>? = get("Tratamiento") as? ArrayList<*>
    val vaccines: ArrayList<*>? = get("Cartilla2") as? ArrayList<*>
    val medicalRecordList: ArrayList<Medicamento> = arrayListOf()

    vaccines?.forEach { vacc ->
        val hash = vacc as HashMap<*, *>
        medicalRecordList.add(
            Medicamento(
                nombre = hash["nombre"].toString(),
                numero_medicamento = hash["numero_medicamento"].toString(),
                tipo = hash["tipo"].toString()

            )
        )
    }

    return RecordData(
        medicalMatter, licence, comments, diagnosis, date, treatment, medicalRecordList
    )
}

data class Medicamento(
    val nombre: String = "",
    val numero_medicamento: String = "",
    val tipo: String = ""
)