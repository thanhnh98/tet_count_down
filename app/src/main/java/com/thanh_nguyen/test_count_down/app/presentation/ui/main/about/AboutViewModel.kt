package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about


import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.domain.usecases.AdsUseCase
import com.thanh_nguyen.test_count_down.app.model.AdsInfoModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import com.thanh_nguyen.test_count_down.app.model.response.Result.Companion
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AboutViewModel(
    private val adsUseCase: AdsUseCase
): BaseCollectionViewModel() {

    private var _adsInfo: MutableStateFlow<Result<AdsInfoModel>> = MutableStateFlow(Result.loading())
    val adsInfo: StateFlow<Result<AdsInfoModel>> get() = _adsInfo

    fun getAdsInfo(){
        doOnIOContext {
            adsUseCase.getAdsInfo().collect {
                _adsInfo.emit(it)
            }
        }
    }

}