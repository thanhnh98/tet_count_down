package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.saveFileToCache
import com.thanh_nguyen.test_count_down.utils.toJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class ListMusicsViewModel(
        private val musicUsecase: MusicUsecase
    ): BaseCollectionViewModel() {

    private var _listMusicsFlow: MutableStateFlow<Result<ListMusicModel>> = MutableStateFlow(Result.loading(null))
    val listMusicsFlow: StateFlow<Result<ListMusicModel>> get() = _listMusicsFlow

    private var _musicSelected: MutableStateFlow<Result<LocalMusicModel?>> = MutableStateFlow(Result.loading(null))
    val musicSelected: StateFlow<Result<LocalMusicModel?>> get() = _musicSelected

    override fun onCreate() {
        super.onCreate()
        //getListMusics()
    }

    fun uploadMusic(uri: Uri){
        cmn("upload file")
        viewModelScope.launch {
            val music = saveFileToCache(
                uri
            )?.let {
                LocalMusicModel(
                    name = it.name,
                    uri = it.path.toUri().toString()
                ).apply {
                    cmn("saved to cache: ${this?.toJson()}")
                }

            }

            music?.apply {
                cmn("data: ${this.toJson()}")
                _musicSelected.value = Result.success(
                    LocalMusicModel(
                        uri = this.uri,
                        name = this.name
                    )
                )
                AppSharedPreferences.setBackgroundMusic(this)
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