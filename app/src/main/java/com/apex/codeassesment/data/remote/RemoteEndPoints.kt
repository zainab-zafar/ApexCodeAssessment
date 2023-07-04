package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.*

interface RemoteEndPoints {

    @GET("api")
    suspend fun createRandomUser(): Response<ResponseModel>

    @GET("api")
    suspend fun create10RandomUsers(@Query("results") results: String = "10"): Response<ResponseModel>
}
