/*
 * Created by Thanh Nguyen on 11/23/21, 4:01 PM
 */

package com.thanh_nguyen.test_count_down.app.presentation.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.common.SoundManager
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivityGetStartedBinding
import com.thanh_nguyen.test_count_down.utils.isTetOnGoing
import kotlinx.coroutines.*
import org.kodein.di.generic.instance

class GetStartedScreen: BaseActivity<ActivityGetStartedBinding>() {
    private val soundManager: SoundManager by instance()
    private var isGoneToMain = false

    override fun inflateLayout(): Int = R.layout.activity_get_started
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        soundManager.playFireworkSound()
        lifecycleScope.launch {
            delay(2000)
            AppSharedPreferences.setIsVisited(true) 
            if (isTetOnGoing())
                onTetGoing()
            else
                goToMain()
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

    private fun onTetGoing(){
        soundManager.playBackgroundSound()
        with(binding){
            val phraseMap =  Constants.Phrases.listWishes.random()
            val pharse1 = phraseMap.first.split(" ")
            val pharse2 = phraseMap.second.split(" ")
            val texts: MutableList<String> = ArrayList()
            texts.run {
                addAll(pharse1)
                addAll(pharse2)
            }
            val listView = listOf(
                tv1,
                tv2,
                tv3,
                tv4,
                tv5,
                tv6,
                tv7,
                tv8
            )

            listView.forEachIndexed { index, textView ->
                textView.text = texts[index]
            }

            animateView(imgHpny, alpha = 0.5f, scaleX = 0.5f, scaleY = 0.5f){
                imgHpny.alpha = 0.5f
                imgHpny.scaleX = 0.5f
                imgHpny.scaleY = 0.5f
                animateView(tv1){
                    animateView(tv2){
                        animateView(tv3){
                            animateView(tv4){
                                animateView(tv5){
                                    animateView(tv6){
                                        animateView(tv7){
                                            animateView(tv8){

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
        if (isTetOnGoing())
            soundManager.pauseBackgroundSound()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopFireworkSound()
        if (isTetOnGoing())
            soundManager.stopBackgroundSound()
    }

    private fun animateView(view1: View,
                            alpha: Float = 1f,
                            scaleX: Float = 1f,
                            scaleY: Float = 1f,
                            callback: () -> Unit){
        view1.animate()
            .setDuration(500L)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    view1.alpha = 1f
                    callback.invoke()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .alpha(alpha)
            .scaleX(scaleX)
            .scaleY(scaleY)
            .start()
    }
}