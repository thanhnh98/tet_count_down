package com.thanh_nguyen.test_count_down.di

import androidx.lifecycle.ViewModelProvider
import bindViewModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.home.HomeViewModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.profile.ProfileViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


/**
 * module for view model dependencies
 */

const val VIEW_MODEL_MODULE = "view_model_module"

val viewModelModule = Kodein.Module(VIEW_MODEL_MODULE, false) {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }

    bindViewModel<HomeViewModel>() with provider {
        HomeViewModel(instance())
    }

    bindViewModel<ProfileViewModel>() with provider {
        ProfileViewModel()
    }


}