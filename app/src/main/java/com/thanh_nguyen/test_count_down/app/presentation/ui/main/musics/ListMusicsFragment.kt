package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicItemView
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentListMusicsBinding
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.createMedia
import com.thanh_nguyen.test_count_down.utils.onClick
import kodeinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.contract.ActivityResultContracts

import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toFile
import com.thanh_nguyen.test_count_down.utils.saveFileToCache
import java.io.File


class ListMusicsFragment: BaseCollectionFragmentMVVM<FragmentListMusicsBinding, ListMusicsViewModel>() {
    private val soundManager: BackgroundSoundManager by instance()

    override fun initClusters() {
        addCluster(MusicItemView::class.java)
    }

    override fun inflateLayout(): Int = R.layout.fragment_list_musics

    override val viewModel: ListMusicsViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
        binding.tvPlayingMusicName.isSelected = true
        binding.lnlUploadMusic.onClick {
            chooseMp3File.launch(Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "audio/mpeg"
                },
                "Chọn nhạc nền"
            ))
        }
    }

    var chooseMp3File: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent = result?.data ?:return@ActivityResultCallback
                val uri: Uri = data.data?:return@ActivityResultCallback
                val file = File(uri.path).saveFileToCache().apply {
                    cmn("saved: ${this?.path}")
                }
                soundManager.updateBackgroundMusic(
                    createMedia(
                        file?.path?.toUri()?:return@ActivityResultCallback
                    )
                )
            }
        })


    private fun onObserve() {
        lifecycleScope.launchWhenCreated {
            viewModel.listMusicsFlow.collect { it ->
                it.onResultReceived(
                    onLoading = {

                    },
                    onError = {

                    },
                    onSuccess ={ listMusics ->
                        listMusics.data?.data?.apply {
                            showListMusics(this)
                        }
                    }
                )
            }
        }
        lifecycleScope.launch {
            viewModel.musicDownloaded.collect {
                soundManager.updateBackgroundMusic(
                    createMedia(
                        it.data?.path?.toUri()?:return@collect
                    )
                )
            }
        }
    }

    private fun showListMusics(listData: List<MusicModel>){
        recyclerManager.replace(MusicItemView::class.java, createListMusicItems(listData))
    }

    private fun createListMusicItems(listData: List<MusicModel>): List<MusicItemView>{
        val listItems: MutableList<MusicItemView> = ArrayList()

        listData.forEach {
            listItems.add(MusicItemView(it) { music ->
                binding.tvPlayingMusicName.text = music.name + " - " + music.singerName
                viewModel.downloadMusic(it)
            })
        }
        return listItems
    }

    override fun onRefresh() {
        super.onRefresh()
        hideLoading()
    }
}