package com.thanh_nguyen.test_count_down.app.presentation.ui.main.home

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.app.presentation.ui.GetStartedScreen
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.common.MusicState
import com.thanh_nguyen.test_count_down.common.SoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentHomeBinding
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService
import com.thanh_nguyen.test_count_down.utils.*
import kodeinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeFragment: BaseFragmentMVVM<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by kodeinViewModel()
    private val soundManager: SoundManager by lazy {
        (activity as MainActivity).soundManager
    }

    private var isMutedSound: Boolean = false

    override fun inflateLayout(): Int = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y
            when(event.action and MotionEvent.ACTION_MASK){
                MotionEvent.ACTION_DOWN -> {
                    binding.containerFireworks.addView(
                        createLottieView(x, y)
                    )
                }
            }
            false
        }

        viewModel.startCountDown()
        viewModel.getWishes()
        setupBackgroundMusic()
        setup()
        setupOnClick()
    }

    private fun setupOnClick() {
        binding.flImgPinContainer.onClick {
            pinCountDownNoti()
        }

        binding.flImgSoundContainer.onClick {
            changeStatusSound()
        }
        binding.tvMusicName?.onClick {
            (activity as MainActivity).navigateToTab(2, true)
        }
        binding.icGotoMusic?.onClick {
            (activity as MainActivity).navigateToTab(2, true)
        }
        binding.icShare?.onClick {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Cùng đợi đến tết thôiii...\nhttps://play.google.com/store/apps/details?id=com.thanh_nguyen.tet_count_down")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun changeStatusSound() {
        if (isMutedSound) {
            soundManager.notifyChangeState(MusicState.Play())
        }
        else{
            soundManager.notifyChangeState(MusicState.Pause())
        }
    }

    private fun pinCountDownNoti(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lifecycleScope.launch {
                AppSharedPreferences.isClosedCountDownNoti.stateIn(this).collect { isClosed ->
                    if(isClosed == true) {
                        activity?.startForegroundService(
                            Intent(
                                activity ?: return@collect,
                                CountDownForegroundService::class.java
                            )
                        )
                    }
                    else{
                        try {
                            activity?.stopService(
                                Intent(
                                    activity ?: return@collect,
                                    CountDownForegroundService::class.java
                                )
                            )
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }.cancel() //cancel after finished
        }
        else {
            activity?.showToastMessage("Tính năng không hỗ trợ trên phiên bản android hiện tại")

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setup() {
        observeLiveDataChanged(viewModel.homeData){ data ->
            data.date.apply{
                if (isTetOnGoing()){
                    startActivity(Intent(activity?:return@apply, GetStartedScreen::class.java))
                }
                else if (isForeground){
                    with(binding){
                        tvDay.text = day.formatTwoNumber()
                        tvHour.text = hour.formatTwoNumber()
                        tvMinute.text = minute.formatTwoNumber()
                        tvSecond.text = second.formatTwoNumber()
                    }
                }
            }
        }

        observeLiveDataChanged(viewModel.wishesData){ wishData ->
            wishData.data?.apply {
                binding.tvWish.text = this.random()
                binding.tvWish.animate()
                    .alpha(1f).duration = 500
            }
        }

        lifecycleScope.launch {
            AppSharedPreferences.isClosedCountDownNoti.collect { isClosed ->
                if (isClosed == true){
                    binding.imgPin.setImageDrawable(App.getResources().getDrawable(R.drawable.ic_unpin, null))
                }
                else
                    binding.imgPin.setImageDrawable(App.getResources().getDrawable(R.drawable.ic_pin, null))
            }
        }
    }

    private fun setupBackgroundMusic() {
        observeLiveDataChanged(soundManager.musicStateChanged) {
            binding.tvMusicName?.requestFocus()
            when(it){
                is MusicState.Play -> {
                    isMutedSound = false
                    updateSoundUI()
                    binding.ltMusic?.playAnimation()
                }

                is MusicState.Pause -> {
                    isMutedSound = true
                    updateSoundUI()
                    binding.ltMusic?.pauseAnimation()
                }

                is MusicState.UpdateMusic -> {
                    binding.tvMusicName?.text = it.localMusic.name
                    isMutedSound = !it.requestPlay
                    updateSoundUI()
                }

                is MusicState.Stop -> {

                }
            }
        }
    }

    private fun updateSoundUI(){
        if (isMutedSound){
            binding.imgSound.setImageDrawable(
                getDrawable(
                    activity ?: return,
                    R.drawable.ic_volume_off
                )
            )
            binding.ltMusic?.pauseAnimation()
        }
        else
            binding.imgSound.setImageDrawable(
                getDrawable(
                    activity ?: return,
                    R.drawable.ic_volume_on
                )
            )
        binding.ltMusic?.playAnimation()

    }

    private fun createLottieView(x: Float, y: Float): LottieAnimationView{
        val lottieFile =  LottieAnimationView(activity).apply {
            val size = convertDpToPixel(160f).toInt()
            layoutParams = ViewGroup.LayoutParams(size, size)
            setX(x - size/2)
            setY(y - size/2)
            setAnimation("fireworks_click_action.json")
            repeat(1){}
            playAnimation()
        }
        lottieFile.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                removeClickActionAnim(lottieFile)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
        return lottieFile
    }

    private fun removeClickActionAnim(view: View){
        try {
            if (binding.containerFireworks.children.contains(view))
                binding.containerFireworks.removeView(view)
        }catch (e: Exception){

        }
    }

    override fun onResume() {
        super.onResume()
        binding.tvMusicName?.requestFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}