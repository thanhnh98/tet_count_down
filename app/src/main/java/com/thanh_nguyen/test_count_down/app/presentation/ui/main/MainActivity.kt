package com.thanh_nguyen.test_count_down.app.presentation.ui.main

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.home.HomeFragment
import com.thanh_nguyen.test_count_down.common.AdsManager
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.common.notification.pushNotification
import com.thanh_nguyen.test_count_down.databinding.ActivityMainBinding
import com.thanh_nguyen.test_count_down.receiver.AlarmReceiver
import kotlinx.coroutines.*
import org.kodein.di.generic.instance
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val soundManager: BackgroundSoundManager by instance()
    private val adsManager: AdsManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        soundManager.playBackgroundSound()
        setupViewPager()
        adsManager.prepareAds()
            .show(
                this@MainActivity,
                20000,
            )
    }

    override fun onResume() {
        super.onResume()
        soundManager.playBackgroundSound()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundSound()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopBackgroundSound()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            MainStateModel(
                title = "Home",
                fragment = HomeFragment()
            ),
        )
        val adapter = MainStateAdapter(this, fragments)

        binding.vpMain.adapter = adapter
        binding.vpMain.isUserInputEnabled = false
    }

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun onBackPressed() {
        finish()
    }
}