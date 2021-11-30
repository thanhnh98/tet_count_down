package com.thanh_nguyen.test_count_down.app.presentation.ui.main.home

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import com.airbnb.lottie.LottieAnimationView
import com.okxe.app.util.convertDpToPixel
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.RemainTimeWidget
import com.thanh_nguyen.test_count_down.app.presentation.ui.SplashScreen
import com.thanh_nguyen.test_count_down.common.AdsManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentHomeBinding
import com.thanh_nguyen.test_count_down.external.firebase.AppAnalytics
import com.thanh_nguyen.test_count_down.utils.formatTwoNumber
import com.thanh_nguyen.test_count_down.utils.observeLiveDataChanged
import com.thanh_nguyen.test_count_down.utils.onClick
import kodeinViewModel
import org.kodein.di.generic.instance

class HomeFragment: BaseFragmentMVVM<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by kodeinViewModel()
    private val adsManager: AdsManager by instance()
    private var adsClickCount = 1

    override fun inflateLayout(): Int = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adsManager.prepareAds()
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
            adsClickCount++.apply {
                if (this % 15 == 0)
                    adsManager.show(
                        activity?:return@apply,
                        5000,
                    )
            }
            false
        }

        viewModel.startCountDown()
        viewModel.getWishes()
        setup()
        setupOnClick()
    }

    private fun setupOnClick() {
        binding.imgOpenWidget.onClick {
            AppAnalytics.trackEventClickOpenWidget()
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val myProvider = ComponentName(activity?:return@onClick, RemainTimeWidget::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && appWidgetManager.isRequestPinAppWidgetSupported) {
                val successCallback = PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(activity, SplashScreen::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

                appWidgetManager.requestPinAppWidget(myProvider, null, successCallback)
                AppAnalytics.trackEventCouldOpenWidget()
            }
            else {
                Toast.makeText(activity, "Thiết bị không hỗ trợ tạo widget trực tiếp", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setup() {
        observeLiveDataChanged(viewModel.homeData){ data ->
            data.date.apply{
                if (isForeground){
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
}