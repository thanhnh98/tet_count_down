package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicItemView
import com.thanh_nguyen.test_count_down.common.BackgroundSoundManager
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentListMusicsBinding
import kodeinViewModel
import kotlinx.coroutines.flow.collect
import org.kodein.di.generic.instance


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
    }

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
    }

    private fun showListMusics(listData: List<MusicModel>){
        recyclerManager.replace(MusicItemView::class.java, createListMusicItems(listData))
    }

    private fun createListMusicItems(listData: List<MusicModel>): List<MusicItemView>{
        val listItems: MutableList<MusicItemView> = ArrayList()

        listData.forEach {
            listItems.add(MusicItemView(it){})
        }
        return listItems
    }
}