package com.thanh_nguyen.baseproject.app.presentation.ui.main.profile

import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.baseproject.databinding.FragmentProfileBinding
import kodeinViewModel

class ProfileFragment: BaseFragmentMVVM<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_profile
}