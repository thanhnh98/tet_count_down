package com.thanh_nguyen.test_count_down.common.base.mvvm.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseActivity<DB: ViewDataBinding>: AppCompatActivity(), KodeinAware {
    open lateinit var binding: DB

    override val kodein by kodein()

    @LayoutRes
    abstract fun inflateLayout(): Int

    open fun inflateStatusColor() = App.getInstance().getColor(R.color.colorPrimary)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableFullScreen()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, inflateLayout())
    }

    private fun enableFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }

    fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun setStatusBarColor(@DrawableRes drawableColor: Int = inflateStatusColor()) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = drawableColor
        }
    }

    /**
    * If increase version code to 30 (R) , remove brackets
     * */
    private fun hideSystemUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }else{
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

    open fun enableLightStatusBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
    }
}