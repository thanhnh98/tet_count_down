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

        binding.tvMusicName.text = AppPreferences.getCurrentBackgroundMusic()?.name?:"Happy new year - V/N"
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
                "Năm Nhâm Dần có gì?",
                "Năm 2022 là năm Nhâm Dần hay còn gọi là năm con Hổ. Năm 2022 âm lịch được tính từ ngày 01/02/2022 đến hết ngày 21/01/2023 theo lịch dương. Về cuộc sống, người sinh năm 2022 Nhâm Dần sẽ có cuộc sống tràn ngập niềm vui. Trong công việc, mọi thứ đều tiến triển thuận lợi và thuận buồm xuôi gió."
            )
        )
        showListSectionData(listOf(
            AboutItemDataModel(
                "Tết 2022 vào ngày nào?",
                "Theo lịch vạn niên, mùng 1 Tết Nguyên đán Nhâm Dần 2022 sẽ rơi vào thứ Ba ngày 01/02/2022 dương lịch.\n" +
                        "\n" +
                        "Trong dịp Tết Nguyên đán năm nay, ngày mùng 4 âm lịch (tức ngày 4/2/2022) chính là ngày lập xuân.",
                activity?.getDrawable(R.drawable.lich_nghi_tet),
                "Lịch nghỉ tết 2022 (Nguồn: eBH)"
            ),
            AboutItemDataModel(
                "Tết đến rồi, về nhà thôi!",
                "Lại một năm nữa sắp qua đi, kết thúc một năm cũng chính là một khởi đầu cho một năm mới, khởi đầu cho mọi sự tốt lành mới sắp đến." +
                        "\n" +
                        "\n" +
                        "Chắc chắn đến thời điểm này tất cả mọi người đều cố gắng hoàn thành nốt những công việc còn đang dang dở để cùng về đoàn tụ với gia đình, cùng chia ngọt sẻ bùi, tâm sự với nhau mọi điều trong cuộc sống và cùng nhau đón Tết trong niềm vui của gia đình, người thân." +
                        "\n" +
                        "\n" +
                        "Vậy nhé, cùng nhau từng ngày đón tết 2022 thôi nào.",
                activity?.getDrawable(R.drawable.img_background),
                ""
            ),
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