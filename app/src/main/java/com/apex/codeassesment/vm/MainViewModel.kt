package com.apex.codeassesment.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.RandomUserApplication
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.ResponseModel
import com.apex.codeassesment.data.remote.ApiCallback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application), LifecycleObserver, ApiCallback {
    private lateinit var mContext: Context

    private var userData = MutableSharedFlow<ResponseModel>()
    val observeUserData: SharedFlow<ResponseModel>
        get() = userData.asSharedFlow()

    private var usersDataList = MutableSharedFlow<ResponseModel>()
    val observeUsersDataList: SharedFlow<ResponseModel>
        get() = usersDataList.asSharedFlow()

    init {
        mContext = application as RandomUserApplication
    }

    fun getUser() {
        viewModelScope.launch {
            userRepository.getUser(true, this@MainViewModel as ApiCallback)
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers(this@MainViewModel as ApiCallback)
        }
    }

    override fun onGetUserSuccessResponse(response: ResponseModel) {
        viewModelScope.launch {
            userData.emit(response)
        }
    }

    override fun onGetUsersSuccessResponse(response: ResponseModel) {
        viewModelScope.launch {
            usersDataList.emit(response)
        }
    }

}