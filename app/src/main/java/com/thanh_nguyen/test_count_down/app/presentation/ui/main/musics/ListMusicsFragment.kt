package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppPreferences
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicDefaultItemView
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicItemView
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.MusicState
import com.thanh_nguyen.test_count_down.common.SoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentListMusicsBinding
import com.thanh_nguyen.test_count_down.utils.*
import kodeinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ListMusicsFragment: BaseCollectionFragmentMVVM<FragmentListMusicsBinding, ListMusicsViewModel>() {

    private val soundManager: SoundManager by lazy {
        (activity as MainActivity).soundManager
    }

    override fun initClusters() {
        addCluster(MusicItemView::class.java)
        addCluster(MusicDefaultItemView::class.java)
    }

    override fun inflateLayout(): Int = R.layout.fragment_list_musics

    override val viewModel: ListMusicsViewModel by kodeinViewModel()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                chooseMp3File()
            } else {
                activity?.showToastMessage("permission Denied")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
        setUpAds()
        showDefaultMusicItem()

        binding.tvPlayingMusicName.isSelected = true
        binding.lnlUploadMusic.onClick {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.vHome.onClick {
            (activity as MainActivity).navigateToTab(0, true)
        }

        binding.tvPlayingMusicName.text = AppPreferences.getCurrentBackgroundMusic()?.name?:"Happy new year - N/A"

        val isMuted = AppPreferences.isBackgroundMuted
        if (isMuted)
            binding.ltMusic.pauseAnimation()
        else
            binding.ltMusic.playAnimation()
    }

    private fun setUpAds() {
        viewModel.getAdsInfo()
    }

    private fun requestPermission(permission: String){
        checkPermission(
            activity = activity as AppCompatActivity,
            permission = permission,
            onGranted = {
                chooseMp3File()
            },
            shouldShowRequestPermissionRationable = {
                activity?.showToastMessage("shouldShowRequestPermissionRationable")
            },
            requestPermission = { permission ->
                requestPermissionLauncher.launch(permission)
            }
        )
    }

    private fun chooseMp3File(){
        chooseMp3Result.launch(Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "audio/mpeg"
            },
            "Chọn nhạc nền"
        ))
    }

    private var chooseMp3Result: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent = result?.data ?:return@ActivityResultCallback
                val uri: Uri = data.data?:return@ActivityResultCallback
                viewModel.uploadMusic(
                    uri,
                )
            }
        }
    )

    private fun onObserve() {
        lifecycleScope.launchWhenCreated {
            viewModel.listMusicsLocal.collect{
                showListMusicsLocal(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.adsInfo.collect {
                it.onResultReceived(
                    onLoading = {

                    },
                    onError = {

                    },
                    onSuccess = {
                        binding.lnlAds.addView(
                            createAdsView(getString(R.string.key_ads_banner_music)).apply {
                                loadAd(AdRequest.Builder().build().apply {
                                    Log.e("CHECKING IS ADS TEST DEVICE", "${isTestDevice(context)}")
                                })
                            }
                        )
                    }
                )
            }
        }
        observeLiveDataChanged(soundManager.musicStateChanged) {
                when(it){
                    is MusicState.Play -> {
                        binding.ltMusic.playAnimation()
                    }

                    is MusicState.Pause -> {
                        binding.ltMusic.pauseAnimation()
                    }

                    is MusicState.UpdateMusic -> {
                        binding.tvPlayingMusicName.text = it.localMusic.name
                    }

                    is MusicState.Stop -> {

                    }
                }
        }

        lifecycleScope.launch {
            viewModel.musicSelected.collect {
//                binding.tvPlayingMusicName.text = it.data?.name
                    it.data?.apply {
                        soundManager.notifyChangeState(
                            MusicState.UpdateMusic(this, !AppPreferences.isBackgroundMuted)
                        )
                    }
            }
        }

        lifecycleScope.launch {
            viewModel.newMusic.collect {
                it?.apply {
                    recyclerManager.append(
                        MusicItemView::class.java,
                        0,
                        MusicItemView(
                            this,
                            onItemSelected = {
                                viewModel.updateBackgroundMusic(it)
                            },
                            onRemoveItemSelected = {
                                viewModel.removeMusic(it)
                            },
                        )
                    )
                }
            }
        }
    }

    private fun createAdsView(adsId: String): AdView {
        return AdView(activity).apply {
            adSize = AdSize.BANNER
            adUnitId = adsId
        }
    }

    private fun showListMusicsLocal(listData: List<LocalMusicModel>){
        recyclerManager.replace(MusicItemView::class.java, createListMusicItems(listData))
    }

    private fun showDefaultMusicItem(){
        recyclerManager.replace(MusicDefaultItemView::class.java, MusicDefaultItemView{
            if (AppPreferences.getCurrentBackgroundMusic()?.name != Constants.DEFAULT_MUSIC_NAME) {
                (activity as MainActivity).requestMusicDefault()
            }
        })
    }

    private fun createListMusicItems(listData: List<LocalMusicModel>): List<MusicItemView>{
        val listItems: MutableList<MusicItemView> = ArrayList()

        listData.forEach {
            listItems.add(
                MusicItemView(
                    it,
                    onItemSelected = {
                        viewModel.updateBackgroundMusic(it)
                    },
                    onRemoveItemSelected = {
                        viewModel.removeMusic(it)
                    })
            )
        }
        return listItems
    }

    override fun onRefresh() {
        super.onRefresh()
        hideLoading()
    }
}