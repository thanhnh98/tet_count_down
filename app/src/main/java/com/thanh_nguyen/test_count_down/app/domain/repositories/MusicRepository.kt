package com.thanh_nguyen.test_count_down.app.domain.repositories

import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface MusicRepository {
    fun downloadMusic(fileUrl: String): Flow<Result<ResponseBody>>
    fun getListMusics(): Flow<Result<ListMusicModel>>
    suspend fun getListMusicsLocal(): List<LocalMusicModel>?
    suspend fun addMusic(music: LocalMusicModel)
    suspend fun deleteMusic(music: LocalMusicModel)
}