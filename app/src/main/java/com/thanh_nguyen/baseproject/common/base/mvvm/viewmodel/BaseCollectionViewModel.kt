package com.thanh_nguyen.baseproject.common.base.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.thanh_nguyen.baseproject.common.event.SingleLiveEvent

abstract class BaseCollectionViewModel: BaseViewModel() {
    private val _isLoadingMore = SingleLiveEvent<Boolean>().apply { value = false }
    var isLoadingMore: LiveData<Boolean> = _isLoadingMore
    var after: String? = null

    fun invokeLoadMore(){
        if (_isLoadingMore.value == false)
            loadMore()
    }

    open fun loadMore(){
        showLoadingMore()
    }

    open fun refreshState(){
        after = null
        hideLoadingMore()
    }

    open fun showLoadingMore(){
        _isLoadingMore.postValue(true)
    }

    open fun hideLoadingMore(){
        _isLoadingMore.postValue(false)
    }
}