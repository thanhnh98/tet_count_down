package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppPreferences
import com.thanh_nguyen.test_count_down.app.domain.usecases.AdsUseCase
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import com.thanh_nguyen.test_count_down.app.model.AdsInfoModel
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.test_count_down.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListMusicsViewModel(
        private val musicUsecase: MusicUsecase,
        private val adsUseCase: AdsUseCase
    ): BaseCollectionViewModel() {
    private var _musicSelected: MutableStateFlow<Result<LocalMusicModel?>> = MutableStateFlow(Result.loading(null))
    val musicSelected: StateFlow<Result<LocalMusicModel?>> get() = _musicSelected

    private var _listMusicsLocal: MutableStateFlow<List<LocalMusicModel>> = MutableStateFlow(emptyList())
    val listMusicsLocal: MutableStateFlow<List<LocalMusicModel>> = _listMusicsLocal

    private var _newMusic: MutableStateFlow<LocalMusicModel?> = MutableStateFlow(null)
    val newMusic: MutableStateFlow<LocalMusicModel?> = _newMusic

    private var _adsInfo: MutableStateFlow<Result<AdsInfoModel>> = MutableStateFlow(Result.loading())
    val adsInfo: StateFlow<Result<AdsInfoModel>> get() = _adsInfo

    override fun onCreate() {
        super.onCreate()
        getListMusicsLocal()
    }

    fun uploadMusic(uri: Uri){
        viewModelScope.launch {
            val fileCached = findCacheByUri(uri)

            if (fileCached != null) {
                Toast.makeText(App.getInstance(), "bài hát ${fileCached.name} đã tồn tại", Toast.LENGTH_SHORT).show()
                return@launch
            }

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

    fun removeMusic(music: LocalMusicModel){
        viewModelScope.launch {
            if (music.uri == AppPreferences.getCurrentBackgroundMusic()?.uri) {
                Toast.makeText(App.getInstance(), "Không thể xóa bài hát đang phát", Toast.LENGTH_SHORT).show()
                return@launch
            }

            musicUsecase.deleteMusic(music)
            deleteFile(music.uri.toUri())
            getListMusicsLocal()
            Toast.makeText(App.getInstance(), "Đã xóa", Toast.LENGTH_SHORT).show()
        }
    }

    fun getListMusicsLocal(){
        viewModelScope.launch {
            musicUsecase.getListMusicsLocal()?.apply {
                _listMusicsLocal.value = this
            }
        }
    }

    fun getAdsInfo(){
        viewModelScope.launch {
            adsUseCase.getAdsInfo().collect {
                _adsInfo.emit(it)
            }
        }
    }
}