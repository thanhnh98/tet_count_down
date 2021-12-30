package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentListMusicsBinding
import com.thanh_nguyen.test_count_down.utils.observeLiveDataChanged
import com.thanh_nguyen.test_count_down.utils.onClick
import com.thanh_nguyen.test_count_down.utils.showToastMessage
import kodeinViewModel
import okhttp3.ResponseBody
import org.kodein.di.generic.instance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


class ListMusicsFragment: BaseCollectionFragmentMVVM<FragmentListMusicsBinding, ListMusicsViewModel>() {
    private val soundManager: BackgroundSoundManager by instance()

    override fun initClusters() {

    }

    override fun inflateLayout(): Int = R.layout.fragment_list_musics

    override val viewModel: ListMusicsViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDownload.onClick {
            activity?.showToastMessage("Clicked")
            viewModel.downloadMusic(
                "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4977930c-98fa-492d-9391-6b78d0dba4a0/HanhPhucXuanNgoi-NooPhuocThinh-6407403_%281%29.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211230%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211230T090407Z&X-Amz-Expires=86400&X-Amz-Signature=08f5c9cc3af3c2e9f6a339376c1988dd3c98d0d28eca307dc5a8557753b54225&X-Amz-SignedHeaders=host&x-id=GetObject"
            )
        }

        observeLiveDataChanged(viewModel.homeData){
            soundManager.stopBackgroundSound()
            val myUri: Uri? = it?.path?.toUri() // initialize Uri here
            val mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(App.getInstance(), myUri?:return@apply)
                prepare()
                start()
            }
        }
    }
}