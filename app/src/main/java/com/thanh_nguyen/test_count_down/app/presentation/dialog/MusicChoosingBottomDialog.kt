package com.thanh_nguyen.test_count_down.app.presentation.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.response.onResultReceived
import com.thanh_nguyen.test_count_down.app.presentation.dialog.items.MusicDefaultItemView
import com.thanh_nguyen.test_count_down.app.presentation.dialog.items.MusicItemView
import com.thanh_nguyen.test_count_down.common.Constants.Companion.DEFAULT_MUSIC_NAME
import com.thanh_nguyen.test_count_down.common.base.adapter.RecycleViewItem
import com.thanh_nguyen.test_count_down.common.base.adapter.RecyclerManager
import com.thanh_nguyen.test_count_down.databinding.FragmentMusicBottomSheetBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MusicChoosingBottomDialog private constructor(): BottomSheetDialogFragment() {

    private var recyclerManager = RecyclerManager<Any>()
    private var gridLayoutManager = GridLayoutManager(context, 1)
    private var onDefaultMusicSelected: (() -> Unit)? = null
    private var onMusicSelected: ((LocalMusicModel) -> Unit)? = null
    private val defaultMusicSelected by lazy {
        arguments?.getSerializable(DEFAULT_MUSIC_SELECTED) as LocalMusicModel?
    }
    private val viewModel = MusicChoosingBottomViewModel()

    companion object {
        const val DEFAULT_MUSIC_SELECTED = "DEFAULT_MUSIC_SELECTED"
        operator fun invoke(defaultMusicSelected: LocalMusicModel?): MusicChoosingBottomDialog{
            return MusicChoosingBottomDialog().apply {
                arguments = bundleOf(
                    DEFAULT_MUSIC_SELECTED to defaultMusicSelected
                )
            }
        }
        var musicItemPositionSelected = -1
    }

    private lateinit var binding: FragmentMusicBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClusters()
        with(binding.recyclerView){
            layoutManager = gridLayoutManager
            adapter = recyclerManager.adapter
        }
        setupObserver()
        viewModel.getAllLocalMusics()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.listLocalMusics.collect {
                it.onResultReceived(
                    onSuccess = {
                         it.data?.onEachIndexed { index, music ->
                             if (music.name == DEFAULT_MUSIC_NAME){
                                 musicItemPositionSelected = 0
                                 return@onEachIndexed
                             }
                             if (it.equals(music)){
                                 musicItemPositionSelected = index + 1
                                 return@onEachIndexed
                             }
                         }?.apply {
                             binding.tvLoading.visibility = View.GONE
                             showListItems(this)
                         }
                    },
                    onError = {

                    },
                    onLoading = {

                    }
                )
            }
        }
    }

    private fun initClusters() {
        addCluster(MusicItemView::class.java)
    }

    private fun addCluster(cluster: Any){
        recyclerManager.addCluster(cluster)
    }

    fun show(fragmentManager: FragmentManager){
        show(fragmentManager, this::class.java.toString())
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    fun onDefaultItemSelected(invoker: () -> Unit): MusicChoosingBottomDialog{
        this.onDefaultMusicSelected = invoker
        return this
    }

    fun onItemSelected(invoker: (LocalMusicModel) -> Unit): MusicChoosingBottomDialog{
        this.onMusicSelected = invoker
        return this
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showListItems(listData: List<LocalMusicModel>){
        val listItems: MutableList<RecycleViewItem<*>> = ArrayList()
        listItems.add(MusicDefaultItemView{ position ->
            musicItemPositionSelected = position
            recyclerManager.adapter.notifyDataSetChanged()
            onDefaultMusicSelected?.invoke()
        })
        listData.forEach {
            listItems.add(MusicItemView(it){ position, music ->
                musicItemPositionSelected = position
                recyclerManager.adapter.notifyDataSetChanged()
                onMusicSelected?.invoke(music)
            })
        }
        recyclerManager.replace(MusicItemView::class.java, listItems)
    }
}