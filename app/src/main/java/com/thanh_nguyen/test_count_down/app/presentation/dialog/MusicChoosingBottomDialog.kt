package com.thanh_nguyen.test_count_down.app.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicDefaultItemView
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items.MusicItemView
import com.thanh_nguyen.test_count_down.common.base.adapter.RecycleViewItem
import com.thanh_nguyen.test_count_down.common.base.adapter.RecyclerManager
import com.thanh_nguyen.test_count_down.databinding.FragmentMusicBottomSheetBinding
import com.thanh_nguyen.test_count_down.utils.getAllMusic

class MusicChoosingBottomDialog private constructor(): BottomSheetDialogFragment() {

    var recyclerManager = RecyclerManager<Any>()
    var gridLayoutManager = GridLayoutManager(context, 1)
    var defaultMusicSelected: (() -> Unit)? = null
    var musicSelected: ((LocalMusicModel) -> Unit)? = null

    companion object {
        operator fun invoke(): MusicChoosingBottomDialog{
            return MusicChoosingBottomDialog()
        }
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
        lifecycleScope.launchWhenResumed {
            getAllMusic().apply {
                showListItems(this)
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
        this.defaultMusicSelected = invoker
        return this
    }

    fun onItemSelected(invoker: (LocalMusicModel) -> Unit): MusicChoosingBottomDialog{
        this.musicSelected = invoker
        return this
    }

    private fun showListItems(listData: List<LocalMusicModel>){
        val listItems: MutableList<RecycleViewItem<*>> = ArrayList()
        listItems.add(MusicDefaultItemView{
            defaultMusicSelected?.invoke()
        })
        listData.forEach {
            listItems.add(MusicItemView(it){
                musicSelected?.invoke(it)
            })
        }
        recyclerManager.replace(MusicItemView::class.java, listItems)
    }
}