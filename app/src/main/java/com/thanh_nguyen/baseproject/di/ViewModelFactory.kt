package com.thanh_nguyen.baseproject.di

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DKodein
import org.kodein.di.generic.instanceOrNull

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?)
            ?: throw IllegalArgumentException("${modelClass.simpleName} Not Found")
    }
}