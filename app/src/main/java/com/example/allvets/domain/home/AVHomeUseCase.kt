package com.example.allvets.domain.home

import com.example.allvets.data.remote.model.Quotes
import com.example.allvets.data.remote.model.UserDataClass
import com.example.allvets.data.remote.model.mapToAllQuotesDataClass
import com.example.allvets.data.remote.model.mapTouserDataClass
import com.example.allvets.presentation.home.SendInfoDate
import com.example.allvets.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AVHomeUseCase @Inject constructor(private val repository: AVHomeRepo){

    suspend operator fun invoke(idUser: String): Flow<Resource<UserDataClass?>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshot = repository.getMyUser(idUser)
                val userData = documentSnapshot?.mapTouserDataClass()
                emit(Resource.Success(userData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun getAllQuotes(idConsult: String): Flow<Resource<MutableList<Quotes>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshots = repository.getPatient(idConsult = idConsult)
                val allQuotes = documentSnapshots.mapToAllQuotesDataClass().allQuotes
                emit(Resource.Success(allQuotes))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun sendDate(dataDate: SendInfoDate): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.sendDate(dataDate)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}