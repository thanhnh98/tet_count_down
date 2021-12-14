package com.thanh_nguyen.test_count_down.app.data.repository.ads

import com.thanh_nguyen.test_count_down.app.data.data_source.remote.AdsRemoteDataSource
import com.thanh_nguyen.test_count_down.app.domain.repositories.AdsRepository
import com.thanh_nguyen.test_count_down.app.model.AdsInfoModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow

class AdsRepositoryImpl(
    private val adsDataSource: AdsRemoteDataSource
): AdsRepository {
    override fun getAdsInfo(): Flow<Result<AdsInfoModel>> = adsDataSource.getAdsInfo()

}