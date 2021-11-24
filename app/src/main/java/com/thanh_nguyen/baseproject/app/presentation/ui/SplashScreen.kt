/*
 * Created by Thanh Nguyen on 11/23/21, 4:01 PM
 */

package com.thanh_nguyen.baseproject.app.presentation.ui

import android.content.Intent
import android.os.Bundle
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.baseproject.common.BackgroundSoundManager
import com.thanh_nguyen.baseproject.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.baseproject.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SplashScreen: BaseActivity<ActivitySplashBinding>() {
    private val soundManager: BackgroundSoundManager by instance()
    override fun inflateLayout(): Int = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager.playFireworkSound()
        GlobalScope.launch {
            delay(2000)
            with(Dispatchers.Main){
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        }
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