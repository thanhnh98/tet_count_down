package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListMusicsViewModel(
        private val musicUsecase: MusicUsecase
    ): BaseCollectionViewModel() {

    private var _listMusicsFlow: MutableStateFlow<Result<ListMusicModel>> = MutableStateFlow(Result.loading(null))
    val listMusicsFlow: StateFlow<Result<ListMusicModel>> get() = _listMusicsFlow

    override fun onCreate() {
        super.onCreate()
        getListMusics()
    }

    fun downloadMusic(fileUrl: String){
        viewModelScope.launch {
            musicUsecase.downloadMusic(fileUrl).collect {
                //download
            }
        }
    }

    fun getListMusics(){
        viewModelScope.launch {
            musicUsecase.getListMusics().collect {
                _listMusicsFlow.value = it
            }
        }
    }
}