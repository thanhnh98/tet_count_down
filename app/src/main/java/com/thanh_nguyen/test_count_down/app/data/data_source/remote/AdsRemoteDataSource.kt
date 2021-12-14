package com.thanh_nguyen.test_count_down.app.data.data_source.remote

import com.thanh_nguyen.test_count_down.app.data.service.AdsService
import com.thanh_nguyen.test_count_down.app.data.service.EventService

class AdsRemoteDataSource(private val adsService: AdsService): BaseRemoteDataSource() {
    fun getAdsInfo() = getResult {
        adsService.getAdsInfo()
    }
}