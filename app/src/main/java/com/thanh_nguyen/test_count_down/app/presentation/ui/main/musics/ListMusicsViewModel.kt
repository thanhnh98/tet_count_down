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

    private var _adsInfo: MutableStateFlow<Result<AdsInfoModel>> = MutableStateFlow(Result.loading())
    val adsInfo: StateFlow<Result<AdsInfoModel>> get() = _adsInfo

    fun updateBackgroundMusic(music: LocalMusicModel){
        doOnIOContext {
            val cached = music.clone()

            saveFileToCache(
                cached.uri.toUri()
            )?.let { cachedUri ->
                cached.uri = cachedUri.toString()
            }

            AppPreferences.saveCurrentBackgroundMusic(cached)
            _musicSelected.value = Result.success(
                cached
            )
        }
    }

    fun getAdsInfo(){
        doOnIOContext {
            adsUseCase.getAdsInfo().collect {
                _adsInfo.emit(it)
            }
        }
    }
}