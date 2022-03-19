package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.Manifest
import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppPreferences
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.dialog.MusicChoosingBottomDialog
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.MainActivity
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.MusicState
import com.thanh_nguyen.test_count_down.common.SoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentListMusicsBinding
import com.thanh_nguyen.test_count_down.external.KeyStore
import com.thanh_nguyen.test_count_down.external.firebase.AppAnalytics
import com.thanh_nguyen.test_count_down.provider.AppProvider
import com.thanh_nguyen.test_count_down.utils.*
import kodeinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception


class ListMusicsFragment: BaseFragmentMVVM<FragmentListMusicsBinding, ListMusicsViewModel>() {

    private var diskAnimateController: ViewPropertyAnimator? = null

    private val soundManager: SoundManager by lazy {
        (activity as MainActivity).soundManager
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
        bindMusicData(AppPreferences.getCurrentBackgroundMusic())
        bindVolumeData(AppPreferences.isBackgroundMuted)
        binding.vHome.onClick {
            (activity as MainActivity).navigateToTab(0, true)
        }
        binding.vMute.onClick {
            if (AppPreferences.isBackgroundMuted) {
                soundManager.notifyChangeState(MusicState.Play())
            }
            else{
                soundManager.notifyChangeState(MusicState.Pause())
            }
        }

    }

    private fun bindVolumeData(isMuted: Boolean){
        if (isMuted){
            binding.imgMute.setImageResource(R.drawable.ic_volume_off)
            binding.ltMusic.pauseAnimation()
        }
        else{
            binding.imgMute.setImageResource(R.drawable.ic_volume_on)
            binding.ltMusic.playAnimation()
        }
    }

    private fun bindMusicData(currentBackgroundMusic: LocalMusicModel?) {
        binding.tvPlayingMusicName.isSelected = true
        binding.tvPlayingMusicName.text = currentBackgroundMusic?.title?:Constants.DEFAULT_MUSIC_NAME
        binding.tvSingerName.text = currentBackgroundMusic?.artist?:Constants.DEFAULT_MUSIC_SINGER_NAME
        binding.lnlUploadMusic.onClick {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.imgThumbnail.onClick {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        animateView(binding.imgThumbnail)
        val thumbnail = currentBackgroundMusic?.getThumbnailBitmap()
        if (thumbnail == null) {
            loadImage(
                AppProvider.getDrawable(R.drawable.music_disk),
                binding.imgThumbnail
            )
        }
        else{
            loadImage(
                thumbnail,
                binding.imgThumbnail
            )
        }
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
                activity?.showToastMessage("Cho phép quyền truy cập bộ nhớ đi")
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    data = Uri.fromParts("package", App.getInstance().packageName, null)
                    startActivity(this)
                }
            },
            requestPermission = { permission ->
                requestPermissionLauncher.launch(permission)
            }
        )
    }

    private fun chooseMp3File(){
        MusicChoosingBottomDialog
            .invoke(
                AppPreferences.getCurrentBackgroundMusic()
            )
            .onItemSelected {
                viewModel.updateBackgroundMusic(music = it)
            }
            .onDefaultItemSelected {
                (activity as MainActivity).requestMusicDefault()
            }
            .show(activity?.supportFragmentManager?:return)
    }


    private fun onObserve() {
        lifecycleScope.launchWhenCreated {
            viewModel.adsInfo.collect {
                it.onResultReceived(
                    onLoading = {

                    },
                    onError = {

                    },
                    onSuccess = {
                        binding.lnlAds.addView(
                            createAdsView(KeyStore.getAdsBannerMusic()).apply {
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
                        bindVolumeData(false)
                    }

                    is MusicState.Pause -> {
                        bindVolumeData(true)
                    }

                    is MusicState.UpdateMusic -> {
                        bindMusicData(it.localMusic)
                        AppAnalytics.trackChangeMusic(it.localMusic.title)
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
    }

    private fun createAdsView(adsId: String): AdView {
        return AdView(activity).apply {
            adSize = AdSize.BANNER
            adUnitId = adsId
        }
    }

    private fun animateView(view1: View){
        diskAnimateController = view1.animate()
            .rotationBy(720f)
            .setDuration(20000L)
            .apply {
                interpolator = LinearInterpolator()
            }
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    animateView(view1)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
        diskAnimateController?.start()
    }

//    override fun onRefresh() {
//        super.onRefresh()
//        hideLoading()
//    }
}