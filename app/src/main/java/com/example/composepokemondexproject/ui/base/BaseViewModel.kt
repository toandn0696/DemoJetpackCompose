package com.example.composepokemondexproject.ui.base

import androidx.lifecycle.ViewModel
import com.example.composepokemondexproject.common.extension.LoadingAware
import com.example.composepokemondexproject.common.extension.ViewErrorAware

/**
 * Create by Nguyen Thanh Toan on 10/5/21
 *
 */
abstract class BaseViewModel : ViewModel(), LoadingAware, ViewErrorAware

//    private val mutableUiState: MutableLiveData<T> = MutableLiveData<T>()
//
//    fun uiState(): LiveData<T> = mutableUiState
//
//    protected fun setUiState(newState: T) {
//        mutableUiState.postValue(newState)
//    }
