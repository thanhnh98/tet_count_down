package com.thanh_nguyen.baseproject.app.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.common.Constants
import com.thanh_nguyen.baseproject.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.baseproject.databinding.FragmentHomeBinding
import com.thanh_nguyen.baseproject.utils.formatTwoNumber
import com.thanh_nguyen.baseproject.utils.observeLiveDataChanged
import kodeinViewModel

class HomeFragment: BaseFragmentMVVM<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startCountDown()
        bindView()
        setup()
    }

    private fun bindView() {
        binding.tvWish.text =  Constants.arrWishes.random()
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
    }
}