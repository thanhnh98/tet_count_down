package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppPreferences
import com.thanh_nguyen.test_count_down.app.model.AboutHeaderDataModel
import com.thanh_nguyen.test_count_down.app.model.AboutItemDataModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.content.AboutViewItem
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.header.AboutHeaderViewItem
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.MusicState
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentAboutBinding
import com.thanh_nguyen.test_count_down.utils.observeLiveDataChanged
import com.thanh_nguyen.test_count_down.utils.onClick
import kodeinViewModel
import kotlinx.coroutines.flow.collect

class AboutFragment: BaseCollectionFragmentMVVM<FragmentAboutBinding, AboutViewModel>() {

    override val viewModel: AboutViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_about

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)
        setupObserver()
        setUpAds()
        setupData()

        binding.vHome.onClick {
            (activity as MainActivity).navigateToTab(0, true)
        }

        binding.tvMusicName.onClick {
            (activity as MainActivity).navigateToTab(2, true)
        }

        binding.tvMusicName.text = AppPreferences.getCurrentBackgroundMusic()?.name?:Constants.DEFAULT_MUSIC_NAME
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.adsInfo.collect {
                it.onResultReceived(
                    onLoading = {

                    },
                    onError = {

                    },
                    onSuccess = {
                        binding.lnlAds.addView(
                            createAdsView(getString(R.string.key_ads_banner_about)).apply {
                                loadAd(AdRequest.Builder().build().apply {
                                    Log.e("CHECKING IS ADS TEST DEVICE", "${isTestDevice(context)}")
                                })
                            }
                        )
                    }
                )
            }
        }

        observeLiveDataChanged((activity as MainActivity).soundManager.musicStateChanged){
            binding.tvMusicName.requestFocus()
            when(it){
                is MusicState.Play -> {
                }

                is MusicState.Pause -> {
                }

                is MusicState.UpdateMusic -> {
                    binding.tvMusicName.text = it.localMusic.name
                }

                is MusicState.Stop -> {

                }
            }
        }
    }

    private fun createAdsView(adsId: String): AdView{
        return AdView(activity).apply {
            adSize = AdSize.BANNER
            adUnitId = adsId
        }
    }

    private fun setUpAds() {
        viewModel.getAdsInfo()
    }

    private fun setupData() {

        showHeaderItem(
            AboutHeaderDataModel(
                title = getString(R.string.about_header_title),
                content = getString(R.string.about_header_content)
            )
        )
        showListSectionData(listOf(
            AboutItemDataModel(
                title = getString(R.string.about_block_1_title),
                content = getString(R.string.about_block_1_content),
            )
        ))
    }

    override fun initClusters() {
        addCluster(AboutHeaderViewItem::class)
        addCluster(AboutViewItem::class)
    }

    private fun showHeaderItem(headerData: AboutHeaderDataModel){
        recyclerManager.replace(
            AboutHeaderViewItem::class,
            AboutHeaderViewItem(headerData)
        )
    }

    private fun showListSectionData(items: List<AboutItemDataModel>){
        recyclerManager.replace(
            AboutViewItem::class,
            createListAboutItem(items)
        )
    }

    private fun createListAboutItem(items: List<AboutItemDataModel>): List<AboutViewItem> {
        return items.map { aboutItemData ->
             AboutViewItem(
                 aboutItemData
            )
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        hideLoading()
    }

    override fun onResume() {
        super.onResume()
        binding.tvMusicName.requestFocus()
    }
}