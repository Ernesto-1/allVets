package com.example.allvets.domain.medical_record

import com.example.allvets.data.remote.model.MedicalRecordData
import com.example.allvets.data.remote.model.toDataMedicalReport
import com.example.allvets.data.remote.model.toDataPatient
import com.example.allvets.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMedicalRecordUseCase @Inject constructor(private val repository: AVMedicalRecordRepo) {

    suspend operator fun invoke(idUser: String, idPet: String): Flow<Resource<MedicalRecordData>> =
        flow {
            emit(Resource.Loading())
            try {
                val doc = repository.getMedicalRecord(idUser = idUser, idPet = idPet)
                val data = MedicalRecordData(
                    patient = doc.data.toDataPatient().patient,
                    record = doc.medicalRecord.toDataMedicalReport().record
                )
                emit(Resource.Success(data))
            } catch (ex: Exception) {
                emit(Resource.Failure(ex))
            }
        }
}