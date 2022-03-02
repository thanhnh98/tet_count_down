package com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.common.event.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun doOnIOContext(invoker: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(ioContext + job){
                invoker.invoke()
            }
        }
    }
}