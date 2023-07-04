package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.ResponseModel

interface ApiCallback {

    fun onGetUserSuccessResponse(response: ResponseModel)
    fun onGetUsersSuccessResponse(response: ResponseModel)


}