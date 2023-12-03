package com.example.allvets.domain.diagnosis

import com.example.allvets.data.remote.model.AVDiagnosisRequest
import com.example.allvets.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDiagnosisUseCase @Inject constructor(private val repository: AVDiagnosis) {

    suspend operator fun invoke(diagnosis: AVDiagnosisRequest): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.saveDiagnosis(diagnosis = diagnosis)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}