package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.ResponseModel
import com.apex.codeassesment.data.remote.ApiCallback
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.data.remote.RemoteResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings


class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteResponseHandler() {


    private val savedUser = AtomicReference(ResponseModel())

    fun getSavedUser() = localDataSource.loadUser()


    suspend fun getRemoteUser(): Flow<ResponseModel> =
        flow {
            emit(ResponseModel(isLoading = true))
            emit(safeApiCall {
                remoteDataSource.loadRandomUsers()
            })
        }.flowOn(Dispatchers.IO)


    suspend fun getRemoteUsers(): Flow<ResponseModel> =
        flow {
            // emit(ResponseModel(isLoading = true))
            emit(safeApiCall {
                remoteDataSource.load10RandomUsers()
            })
        }.flowOn(Dispatchers.IO)


    suspend fun getUser(forceUpdate: Boolean, apiCallback: ApiCallback) {
        if (forceUpdate) {
            getRemoteUser().collectLatest {
                savedUser.set(it)
            }
        }
        apiCallback.onGetUserSuccessResponse(savedUser.get())
    }

    suspend fun getUsers(apiCallback: ApiCallback) {
        getRemoteUsers().collectLatest {
            apiCallback.onGetUsersSuccessResponse(it)
        }
    }
}

