package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.saveFileToCache
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

    private var _musicDownloaded: MutableStateFlow<Result<File?>> = MutableStateFlow(Result.loading(null))
    val musicDownloaded: StateFlow<Result<File?>> get() = _musicDownloaded

    override fun onCreate() {
        super.onCreate()
        getListMusics()
    }

    fun downloadMusic(music: MusicModel){
        viewModelScope.launch {
            musicUsecase.downloadMusic("https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4977930c-98fa-492d-9391-6b78d0dba4a0/HanhPhucXuanNgoi-NooPhuocThinh-6407403_%281%29.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220104%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220104T082541Z&X-Amz-Expires=86400&X-Amz-Signature=124dc1bea2af78b81825309a37fb2935a6fe889fc3d7cf2f222dead1a5c1dc56&X-Amz-SignedHeaders=host&x-id=GetObject").collect { musicData ->
                _musicDownloaded.value = (
                    Result.success(
                        saveFileToCache(
                            musicData.data,
                            music.name+".mp3",
                        )
                    )
                )
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