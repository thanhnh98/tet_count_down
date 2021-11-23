package com.thanh_nguyen.baseproject.app.presentation.ui.main

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.thanh_nguyen.baseproject.app.presentation.ui.main.home.HomeFragment
import com.thanh_nguyen.baseproject.app.presentation.ui.main.profile.ProfileFragment
import com.thanh_nguyen.baseproject.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.baseproject.databinding.ActivityMainBinding

import android.media.MediaPlayer

import com.thanh_nguyen.baseproject.R


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mediaPlayer by lazy {
        MediaPlayer.create(this, R.raw.tetlatet).apply {
            isLooping = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playSound()
        setupViewPager()
    }

    private fun playSound(){
        if (!mediaPlayer.isPlaying)
            mediaPlayer.start()
    }

    private fun pauseSound(){
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    private fun stopSound(){
        mediaPlayer.stop()
    }

    override fun onResume() {
        super.onResume()
        playSound()
    }

    override fun onPause() {
        super.onPause()
        pauseSound()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSound()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            MainStateModel(
                "Home",
                R.drawable.ic_apple,
                HomeFragment()
            ),
//            MainStateModel(
//                "Profile",
//                R.drawable.ic_apple,
//                ProfileFragment()
//            ),
        )
        val adapter = MainStateAdapter(this, fragments)

        binding.vpMain.adapter = adapter
        binding.vpMain.isUserInputEnabled = false
        TabLayoutMediator(
            binding.tabMain,
            binding.vpMain
        ){ tab, pos ->
            tab.icon = ResourcesCompat.getDrawable(resources, fragments[pos].icon, null)
            tab.text = fragments[pos].title
        }.attach()
    }

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun onBackPressed() {
        finish()
    }
}