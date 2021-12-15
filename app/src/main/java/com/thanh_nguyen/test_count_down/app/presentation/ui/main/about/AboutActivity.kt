package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about

import android.os.Bundle
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivityAboutBinding
import com.thanh_nguyen.test_count_down.utils.addFragment

class AboutActivity: BaseActivity<ActivityAboutBinding>() {
    override fun inflateLayout(): Int = R.layout.activity_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(
            R.id.container,
            AboutFragment(),
            "ABOUT"
        )
    }

    override fun onBackPressed() {
        finish()
    }
}