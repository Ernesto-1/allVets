package com.example.allvets.domain

import com.example.allvets.data.remote.model.Coordinates
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("geocode/json")
    suspend fun getdataFromAddress(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Coordinates
}