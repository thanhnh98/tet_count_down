/*
 * Created by Thanh Nguyen on 11/23/21, 4:01 PM
 */

package com.thanh_nguyen.test_count_down.app.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.BuildConfig
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.common.AdsManager
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivitySplashBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.kodein.di.generic.instance

@SuppressLint("CustomSplashScreen")
class SplashScreen: BaseActivity<ActivitySplashBinding>() {
    private val soundManager: BackgroundSoundManager by instance()
    private val adsManager: AdsManager by instance()
    private var isGoneToMain = false

    override fun inflateLayout(): Int = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (BuildConfig.DEBUG) {
//            goToMain()
//            return
//        }
        soundManager.playFireworkSound()
        adsManager.prepareAds()
        lifecycleScope.launch {
            AppSharedPreferences.isVisited.collect {
                if (isGoneToMain)
                    return@collect

                if (it == true){
                    with(Dispatchers.Main) {
                        adsManager.show(
                            this@SplashScreen,
                            2000,
                            onDismiss = {
                                goToMain()
                            },
                            onFailedToShow = {
                                goToMain()
                            },
                            onOtherException = {
                                goToMain()
                            }
                        )
                    }
                }
                else{
                    delay(2000)
                    with(Dispatchers.Main){
                        AppSharedPreferences.setIsVisited(true)
                        goToMain()
                    }
                }
            }
        }
    }

    private fun goToMain(){
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        isGoneToMain = true
        finish()
    }

    override fun onResume() {
        super.onResume()
        soundManager.playFireworkSound()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseFireworkSound()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopFireworkSound()
    }
}