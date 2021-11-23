/*
 * Created by Thanh Nguyen on 11/23/21, 4:01 PM
 */

package com.thanh_nguyen.baseproject.app.presentation.ui

import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.baseproject.databinding.ActivitySplashBinding

class SplashScreen: BaseActivity<ActivitySplashBinding>() {
    override fun inflateLayout(): Int = R.layout.activity_splash
}