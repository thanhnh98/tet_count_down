package com.thanh_nguyen.baseproject.app.presentation.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.baseproject.app.model.DateDataModel
import com.thanh_nguyen.baseproject.app.model.HomeDataModel
import com.thanh_nguyen.baseproject.common.Constants
import com.thanh_nguyen.baseproject.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.baseproject.utils.getSecondsUntilDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel: BaseCollectionViewModel() {
    private var _homeData = MutableLiveData<HomeDataModel>()
    val homeData: LiveData<HomeDataModel> get() = _homeData

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
}