package com.thanh_nguyen.test_count_down.app.presentation.ui.main

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.RemainTimeWidget
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.calendar.CalendarFragment
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.home.HomeFragment
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivityMainBinding
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService
import org.kodein.di.generic.instance


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val soundManager: BackgroundSoundManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        soundManager.playBackgroundSound()
        setupViewPager()
        sendBroadcast(Intent(this, RemainTimeWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, CountDownForegroundService::class.java))
        }
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
    }

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun onBackPressed() {
        finish()
    }
}