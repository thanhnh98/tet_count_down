package com.thanh_nguyen.test_count_down.app.presentation.ui.main.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.ActivityGameBinding
import com.thanh_nguyen.test_count_down.databinding.FragmentPlaygroundBinding
import kodeinViewModel

class GameFragment: BaseFragmentMVVM<FragmentPlaygroundBinding, GameViewModel>() {
    override fun inflateLayout(): Int = R.layout.fragment_playground

    override val viewModel: GameViewModel by kodeinViewModel()

    override fun onCreateViewX(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        super.onCreateViewX(inflater, container, savedInstanceState)
    }
}