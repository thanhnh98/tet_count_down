package tlife.architect.base.mvvm.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
abstract class BaseCollectionViewModel: BaseViewModel() {
    private val _isLoadingMore = MutableStateFlow(false)
    var isLoadingMore: Flow<Boolean> = _isLoadingMore
    var after: String? = null

    fun invokeLoadMore(){
        if (!_isLoadingMore.value)
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
        _isLoadingMore.value = true
    }

    open fun hideLoadingMore(){
        _isLoadingMore.value = false
    }
}