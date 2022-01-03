package com.thanh_nguyen.test_count_down.app.data.service

import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MusicService {
    @GET
    suspend fun downloadMusic(
        @Url fileUrl: String
    ): Response<ResponseBody>

    @GET("main/musics")
    suspend fun getListMusics(): Response<ListMusicModel>
}