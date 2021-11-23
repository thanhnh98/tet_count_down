package com.thanh_nguyen.baseproject.common.base.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thanh_nguyen.baseproject.common.event.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(){
    private var job: Job = Job()
    var ioContext: CoroutineContext = Dispatchers.IO + job

    private val _isLoading = SingleLiveEvent<Boolean>().apply {
        postValue(false)
    }
    var isLoading: LiveData<Boolean> = _isLoading

    open fun onCreate() {}

    open fun onDestroy() {
        job.cancel()
    }

    open fun hideLoading() {
        _isLoading.postValue(false)
    }

    open fun showLoading() {
        _isLoading.postValue(true)
    }
}