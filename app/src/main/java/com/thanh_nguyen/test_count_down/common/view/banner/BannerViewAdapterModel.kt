package com.thanh_nguyen.test_count_down.common.view.banner

import com.thanh_nguyen.test_count_down.app.model.BannerModel
import com.thanh_nguyen.test_count_down.app.model.BaseModel


sealed class BannerViewAdapterModel(
    val bannerData: BannerModel? = null
): BaseModel(){
    data class HomeHorizontalBannerModel(
        private val home_banner_data: BannerModel? = null
    ): BannerViewAdapterModel(home_banner_data)

    data class ProductDetailBannerModel(
        private val product_detail_bannerData: BannerModel? = null
    ): BannerViewAdapterModel(product_detail_bannerData)
}