package com.thanh_nguyen.test_count_down.app.domain.repositories

import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface MusicRepository {
    fun downloadMusic(fileUrl: String): Flow<Result<ResponseBody>>
}