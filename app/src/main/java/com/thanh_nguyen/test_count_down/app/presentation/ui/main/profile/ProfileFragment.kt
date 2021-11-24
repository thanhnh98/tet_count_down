package com.thanh_nguyen.test_count_down.app.presentation.ui.main.profile

import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentProfileBinding
import kodeinViewModel

class ProfileFragment: BaseFragmentMVVM<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_profile
}