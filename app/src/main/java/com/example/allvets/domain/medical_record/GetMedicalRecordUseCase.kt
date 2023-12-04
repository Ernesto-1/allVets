package com.example.allvets.domain.medical_record

import com.example.allvets.data.remote.model.MedicalRecordData
import com.example.allvets.data.remote.model.toDataMedicalReport
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
                    record = doc.medicalRecord.toDataMedicalReport().record.sortedByDescending {
                        it.date
                    }
                )
                emit(Resource.Success(data))
            } catch (ex: Exception) {
                emit(Resource.Failure(ex))
            }
        }
}