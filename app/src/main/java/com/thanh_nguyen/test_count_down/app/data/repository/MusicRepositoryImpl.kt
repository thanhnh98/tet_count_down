package com.thanh_nguyen.test_count_down.app.data.repository

import com.thanh_nguyen.test_count_down.app.data.data_source.local.LocalMusicDataSource
import com.thanh_nguyen.test_count_down.app.data.data_source.remote.MusicDownloadDataSource
import com.thanh_nguyen.test_count_down.app.domain.repositories.MusicRepository
import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

class MusicRepositoryImpl(
    private val dataSource: MusicDownloadDataSource,
    private val localSource: LocalMusicDataSource
    ): MusicRepository {
    override fun downloadMusic(fileUrl: String): Flow<Result<ResponseBody>> = dataSource.downloadMusic(fileUrl)
    override fun getListMusics(): Flow<Result<ListMusicModel>> = dataSource.getListMusics()
    override suspend fun getListMusicsLocal(): List<LocalMusicModel>? = localSource.getListMusicsLocal()
}