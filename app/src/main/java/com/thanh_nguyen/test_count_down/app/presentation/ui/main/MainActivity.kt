package com.thanh_nguyen.test_count_down.app.presentation.ui.main

import android.animation.Animator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.BuildConfig
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.AboutFragment
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.game.GameFragment
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.HandbookFragment
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.home.HomeFragment
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.common.viewpager_transformer.CubeInPageTransformer
import com.thanh_nguyen.test_count_down.databinding.ActivityMainBinding
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.onClick
import com.thanh_nguyen.test_count_down.utils.setAlarmRemindAfterInterval
import com.thanh_nguyen.test_count_down.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val soundManager: BackgroundSoundManager by instance()
    private var isFirstBackPress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keepOnScreen()
        setupViewPager()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lifecycleScope.launch {
                AppSharedPreferences.isClosedCountDownNoti.collect{
                    if (it != true){
                        startForegroundService(Intent(this@MainActivity, CountDownForegroundService::class.java))
                    }else
                        setAlarmRemindAfterInterval(this@MainActivity)
                }
            }
        }
        binding.ltvSwipe.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                hideSwipeContainer()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

        binding.ctlSwipeContainer.onClick {
            hideSwipeContainer()
        }

        lifecycleScope.launch {
            AppSharedPreferences.isShowedInstruction.collect {
                if (it == true){
                    hideSwipeContainer()
                }
                else
                    showSwipeContainer()
            }
        }
    }

    private fun showSwipeContainer() {
        binding.ctlSwipeContainer.alpha = 0f
        binding.ctlSwipeContainer.visibility = View.VISIBLE
        binding.ctlSwipeContainer
            .animate()
            .alpha(1f)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.ctlSwipeContainer.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .duration = 500
    }

    private fun hideSwipeContainer(){
        binding.ctlSwipeContainer
            .animate()
            .alpha(0f)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                   binding.ctlSwipeContainer.visibility = View.GONE
                    lifecycleScope.launch {
                        AppSharedPreferences.setIsShowedInstruction(true)
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .duration = 500
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundSound()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.pauseBackgroundSound()
    }

    private fun setupViewPager() {
        val fragments = mutableListOf(
            MainStateModel(
                title = "Home",
                fragment = HomeFragment()
            ),
            MainStateModel(
                title = "About",
                fragment = AboutFragment()
            ),
        )
        val adapter = MainStateAdapter(this, fragments)

        with(binding.vpMain) {
            this.adapter = adapter
            setPageTransformer(CubeInPageTransformer())
        }
    }

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun onBackPressed() {
        if (binding.vpMain.currentItem != 0){
            binding.vpMain.setCurrentItem(0, true)
        }
        else
            onFinish()
    }

    private fun onFinish() {
        if (!isFirstBackPress){
            showToastMessage("Bấm lần nữa để thoát")
            isFirstBackPress = true
            lifecycleScope.launch {
                delay(3000)
                isFirstBackPress = false
            }
        }
        else {
            finish()
        }
    }
}