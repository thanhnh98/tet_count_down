package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppPreferences
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
    private var _musicSelected: MutableStateFlow<Result<LocalMusicModel?>> = MutableStateFlow(Result.loading(null))
    val musicSelected: StateFlow<Result<LocalMusicModel?>> get() = _musicSelected

    private var _listMusicsLocal: MutableStateFlow<List<LocalMusicModel>> = MutableStateFlow(emptyList())
    val listMusicsLocal: MutableStateFlow<List<LocalMusicModel>> = _listMusicsLocal

    private var _newMusic: MutableStateFlow<LocalMusicModel?> = MutableStateFlow(null)
    val newMusic: MutableStateFlow<LocalMusicModel?> = _newMusic

    override fun onCreate() {
        super.onCreate()
        getListMusicsLocal()
    }

    fun uploadMusic(uri: Uri){
        viewModelScope.launch {
            val music = saveFileToCache(
                uri
            )?.let {
                LocalMusicModel(
                    name = it.name,
                    uri = it.path.toUri().toString()
                )
            }

            music?.apply {
                addMusic(this)
                updateBackgroundMusic(this)
            }
        }
    }

    fun updateBackgroundMusic(music: LocalMusicModel){
        AppPreferences.saveCurrentBackgroundMusic(music)
        _musicSelected.value = Result.success(
            music
        )
    }

    fun addMusic(music: LocalMusicModel){
        viewModelScope.launch {
            musicUsecase.addMusic(music)
            newMusic.value = music
        }
    }

    fun getListMusicsLocal(){
        viewModelScope.launch {
            musicUsecase.getListMusicsLocal()?.apply {
                _listMusicsLocal.value = this
            }
        }
    }
}