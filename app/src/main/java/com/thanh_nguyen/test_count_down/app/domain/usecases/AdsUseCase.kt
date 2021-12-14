package com.thanh_nguyen.test_count_down.app.domain.usecases

import com.thanh_nguyen.test_count_down.app.domain.repositories.AdsRepository

class AdsUseCase(
    private val adsRepository: AdsRepository
) {
    fun getAdsInfo() = adsRepository.getAdsInfo()
}