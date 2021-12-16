/*
 * Created by Thanh Nguyen on 11/23/21, 4:01 PM
 */

package com.thanh_nguyen.test_count_down.app.presentation.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivityGetStartedBinding
import kotlinx.coroutines.*
import org.kodein.di.generic.instance

class GetStartedScreen: BaseActivity<ActivityGetStartedBinding>() {
    private val soundManager: BackgroundSoundManager by instance()
    private var isGoneToMain = false

    override fun inflateLayout(): Int = R.layout.activity_get_started
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        soundManager.playFireworkSound()
        lifecycleScope.launch {
            delay(2000)
            with(Dispatchers.Main){
                AppSharedPreferences.setIsVisited(true)
                goToMain()
            }
        }

        binding.imgHpny.animate()
            .setDuration(1000)
            .alpha(1f)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.imgFlowerTop.alpha = 1f
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .start()

        binding.imgFlowerTop.animate()
            .setDuration(1000)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.imgFlowerTop.alpha = 1f
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .alpha(1f)
            .start()

        binding.imgFlowerBottom.animate()
            .setDuration(1000)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.imgFlowerBottom.alpha = 1f
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .alpha(1f)
            .start()
    }

    private fun goToMain(){
        startActivity(Intent(this@GetStartedScreen, MainActivity::class.java))
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