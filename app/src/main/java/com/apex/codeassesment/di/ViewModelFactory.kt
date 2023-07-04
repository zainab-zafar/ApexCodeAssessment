package com.apex.codeassesment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apex.codeassesment.vm.MainViewModel


class ViewModelFactory constructor(val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass == MainViewModel::class.java) {
            mainViewModel as T
        } else {
            throw  IllegalStateException("Unknown entity")
        }
}