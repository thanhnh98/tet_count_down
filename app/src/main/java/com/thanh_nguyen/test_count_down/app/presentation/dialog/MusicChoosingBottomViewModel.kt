package com.thanh_nguyen.test_count_down.app.presentation.dialog

import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseViewModel
import com.thanh_nguyen.test_count_down.utils.getAllMusic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicChoosingBottomViewModel: BaseViewModel() {
    private var _listLocalMusics: MutableStateFlow<Result<List<LocalMusicModel>>> = MutableStateFlow(Result.loading(null))
    val listLocalMusics: StateFlow<Result<List<LocalMusicModel>>> = _listLocalMusics

    fun getAllLocalMusics(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getAllMusic().apply {
                    _listLocalMusics.emit(Result.success(this))
                }
            }
        }
    }
}