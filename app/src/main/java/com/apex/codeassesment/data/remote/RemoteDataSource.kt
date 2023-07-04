package com.apex.codeassesment.data.remote

import javax.inject.Inject

// TODO (2 points): Add tests
class RemoteDataSource @Inject constructor(private val remoteEndPoints: RemoteEndPoints) {

    // TODO (5 points): Load data from endpoint https://randomuser.me/api
    suspend fun loadRandomUsers() = remoteEndPoints.createRandomUser()

    // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
    // TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints
    suspend fun load10RandomUsers() = remoteEndPoints.create10RandomUsers()
}
