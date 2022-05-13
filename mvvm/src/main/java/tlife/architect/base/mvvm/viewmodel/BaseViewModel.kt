package tlife.architect.base.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class BaseViewModel : ViewModel(){
    private var job: Job = Job()
    var ioContext: CoroutineContext = Dispatchers.IO + job

    private val _isLoading = MutableStateFlow(false)

    var isLoading: Flow<Boolean> = _isLoading

    open fun onCreate() {}

    open fun onDestroy() {
        job.cancel()
    }

    open fun hideLoading() {
        _isLoading.value = false
    }

    open fun showLoading() {
        _isLoading.value = true
    }

    fun doOnIOContext(invoker: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(ioContext + job){
                invoker.invoke()
            }
        }
    }
}