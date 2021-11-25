package com.thanh_nguyen.test_count_down.common

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.thanh_nguyen.test_count_down.utils.NullableOnClick

class AdsManager(private val context: Context) {
    private var mInterstitialAd: InterstitialAd? = null
    fun prepareAds(): AdsManager{
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    Log.e("loaded","loaded ads")
                }
            })
        return this
    }

    private fun registerListener(
        onDismiss: NullableOnClick = null,
        onFailedToShow: NullableOnClick = null,
        onShowedFullScreen: NullableOnClick = null,
    ){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.e("onDismissed","?")
                onDismiss?.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                onFailedToShow?.invoke()
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
                onShowedFullScreen?.invoke()
            }
        }
    }

    fun show(
        activity: Activity,
        delay: Long = 0,
        onDismiss: NullableOnClick = null,
        onFailedToShow: NullableOnClick = null,
        onShowedFullScreen: NullableOnClick = null,
        onOtherException: NullableOnClick = null
    ){
        Handler(Looper.getMainLooper()).postDelayed({
            if (mInterstitialAd != null) {
                registerListener(onDismiss, onFailedToShow, onShowedFullScreen)
                mInterstitialAd?.show(activity)
            }
            else
                onOtherException?.invoke()
        },delay)
    }
}