package com.thanh_nguyen.test_count_down.external

object KeyStore {
    init {
        System.loadLibrary("secret_keys")
    }

    external fun getAdsInterstitial(): String
    external fun getAdsBannerAbout(): String
    external fun getAdsBannerMusic(): String
}