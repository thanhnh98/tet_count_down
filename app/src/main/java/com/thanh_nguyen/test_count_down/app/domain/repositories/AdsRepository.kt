package com.thanh_nguyen.test_count_down.app.domain.repositories

import com.thanh_nguyen.test_count_down.app.model.AdsInfoModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow


interface AdsRepository {
    fun getAdsInfo(): Flow<Result<AdsInfoModel>>
}