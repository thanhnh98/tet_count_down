package com.thanh_nguyen.test_count_down.app.presentation.ui.main.home

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.ChineseCalendar
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

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
                putExtra(Intent.EXTRA_TEXT, "C??ng ?????i ?????n t???t th??iii...\nhttps://play.google.com/store/apps/details?id=com.thanh_nguyen.tet_count_down")
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
                AppSharedPreferences
                    .isEnabledCountDownNoti
                    .collect{ currentState ->
                        if (currentState != true) {
                            activity?.startForegroundService(
                                Intent(
                                    activity,
                                    CountDownForegroundService::class.java
                                )
                            )
                        } else {
                            activity?.stopService(
                                Intent(
                                    activity,
                                    CountDownForegroundService::class.java
                                )
                            )
                        }
                    }
            }.cancel() //cancel after finished
        }
        else {
            activity?.showToastMessage("T??nh n??ng kh??ng h??? tr??? tr??n phi??n b???n android hi???n t???i")

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
            AppSharedPreferences.isEnabledCountDownNoti.collect { isEnable ->
                if (isEnable != true){
                    binding.imgPin.setImageDrawable(App.getResources().getDrawable(R.drawable.ic_unpin, null))
                }
                else
                    binding.imgPin.setImageDrawable(App.getResources().getDrawable(R.drawable.ic_pin, null))
            }
        }

        buildCalendar()
    }

    private fun buildCalendar() {
        lifecycleScope.launchWhenCreated {
            val calendar = Calendar.getInstance()
            val date = Date(calendar.timeInMillis)

            doOnIO{
                val dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))
                val dayOfMonth  = calendar.get(Calendar.DAY_OF_MONTH)
                val month = (calendar.get(Calendar.MONTH) + 1) % 12
                val year = calendar.get(Calendar.YEAR)
                launch {
                    doOnMain {
                        binding.tvCalendar?.text = "$dayOfWeek, $dayOfMonth/$month/$year"
                        binding.tvCalendar?.fadeInAppearance()
                    }
                }
            }

            doOnIO {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val chineseCalendar = ChineseCalendar(date)
                    val chineseMonth = (chineseCalendar.get(Calendar.MONTH) + 1) % 12
                    val chineseDay = chineseCalendar.get(Calendar.DAY_OF_MONTH)
                    launch {
                        doOnMain {
                            binding.tvChineseCalendar?.visibility = View.VISIBLE
                            binding.tvChineseCalendar?.text = "($chineseDay/$chineseMonth ??m l???ch)"
                            binding.tvChineseCalendar?.fadeInAppearance()
                        }
                    }
                }
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
                    binding.tvMusicName?.text = it.localMusic.title
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