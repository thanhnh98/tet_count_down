package com.thanh_nguyen.test_count_down.app.presentation.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.app.domain.usecases.EventUseCase
import com.thanh_nguyen.test_count_down.app.model.DateDataModel
import com.thanh_nguyen.test_count_down.app.model.HomeDataModel
import com.thanh_nguyen.test_count_down.app.model.WishModel
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.test_count_down.utils.getSecondsUntilDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventUseCase: EventUseCase
): BaseCollectionViewModel() {
    private var _homeData = MutableLiveData<HomeDataModel>()
    val homeData: LiveData<HomeDataModel> get() = _homeData

    private var _wishesData = MutableLiveData<WishModel>()
    val wishesData: LiveData<WishModel> get() = _wishesData

    fun startCountDown(){
        viewModelScope.launch {
            onTick()
        }
    }

    private suspend fun onTick(){
        with(Dispatchers.IO){
            val minuteSec = 60
            val hourSec = minuteSec * 60
            val daySec = hourSec * 24
            val totalSeconds = getSecondsUntilDate(Constants.EventDate.LUNAR_NEW_YEAR)
            val days = totalSeconds /  daySec
            val hours = (totalSeconds - days * daySec) / hourSec
            val minutes = (totalSeconds - days * daySec - hours * hourSec) / minuteSec
            val seconds = totalSeconds - days * daySec - hours * hourSec - minutes * minuteSec
            _homeData.postValue(
                HomeDataModel(
                    DateDataModel.LunarNewYear(
                        dayLunar = days,
                        hourLunar = hours,
                        minuteLunar = minutes,
                        secondLunar = seconds
                    )
                )
            )
        }
        delay(1000)
        onTick()
    }

    fun getWishes(){
        viewModelScope.launch {
            eventUseCase.getWishes().collect {
                it.data?.apply {
                }
                if (it.data == null)
                    _wishesData.postValue(WishModel(data = listOf("Happy new year")))
                else
                    _wishesData.postValue(it.data)
            }
        }
    }
}