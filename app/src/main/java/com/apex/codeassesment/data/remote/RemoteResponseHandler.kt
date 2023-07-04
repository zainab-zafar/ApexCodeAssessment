package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.enums.ApiResponseCodes
import com.apex.codeassesment.data.model.ErrorModel
import com.apex.codeassesment.data.model.ResponseModel
import com.google.gson.Gson
import retrofit2.Response

abstract class RemoteResponseHandler {

    suspend fun safeApiCall(apiCall: suspend () -> Response<ResponseModel>): ResponseModel {
        try {
            val response = apiCall()
            var responseModel: ResponseModel
            return if (response.isSuccessful && response.code() == ApiResponseCodes.CODE_200_SUCCESS.tag.code) {
                response.body() as ResponseModel
            } else {
                Gson().fromJson(response.errorBody()?.string(), ResponseModel::class.java).also { responseModel = it }
                if (responseModel.error != null) {
                    responseModel
                } else {
                    val errorModel = ErrorModel(message = response.message())
                    ResponseModel(isSuccess = false, error = errorModel)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            val errorModel = ErrorModel(message = ex.localizedMessage)

            return ResponseModel(isSuccess = false, error = errorModel)
        }
    }
}
