package com.thanh_nguyen.test_count_down.app.data.service

import com.thanh_nguyen.test_count_down.app.model.AdsInfoModel
import retrofit2.Response
import retrofit2.http.GET

interface AdsService {
    @GET("main/ads_info")
    suspend fun getAdsInfo(): Response<AdsInfoModel>
}