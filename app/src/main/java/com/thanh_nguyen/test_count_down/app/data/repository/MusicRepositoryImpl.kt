package com.thanh_nguyen.test_count_down.app.data.repository

import com.thanh_nguyen.test_count_down.app.data.data_source.remote.MusicDownloadDataSource
import com.thanh_nguyen.test_count_down.app.domain.repositories.MusicRepository
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

class MusicRepositoryImpl(private val dataSource: MusicDownloadDataSource): MusicRepository {
    override fun downloadMusic(fileUrl: String): Flow<Result<ResponseBody>> = dataSource.downloadMusic(fileUrl)
}