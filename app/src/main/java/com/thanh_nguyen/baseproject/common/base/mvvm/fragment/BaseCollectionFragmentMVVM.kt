package com.thanh_nguyen.baseproject.common.base.mvvm.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.common.EndlessRecyclerViewScrollListener
import com.thanh_nguyen.baseproject.common.base.adapter.RecyclerManager
import com.thanh_nguyen.baseproject.common.base.mvvm.viewmodel.BaseCollectionViewModel


abstract class BaseCollectionFragmentMVVM<DB: ViewDataBinding, VM: BaseCollectionViewModel> : BaseFragmentMVVM<DB, VM>() {
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val SPAN_COUNT = 1
    private var isLoadingMore = false
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout
    var recyclerManager = RecyclerManager<Any>()
    var gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClusters()
    }

    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)
        recyclerView = binding.root.findViewById(R.id.recycler_view)
        swipeRefresh = binding.root.findViewById(R.id.swipe_refresh)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (shouldLoadMore()) {
                    Log.e("recycler", "Loadmore")
                    viewModel.invokeLoadMore()
                }
            }
        }

        with(gridLayoutManager){
            reverseLayout = isReverseLayout()
        }

        with(recyclerView){
            layoutManager = gridLayoutManager
            adapter = recyclerManager.adapter
            addOnScrollListener(scrollListener)
        }


        swipeRefresh.also {
            it.setOnRefreshListener {
                if (shouldPullToRefresh())
                    onRefresh()
                else
                    hideLoading()
            }
            it.setColorSchemeColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_200
                )
            )
        }
    }

    abstract fun initClusters()

    fun addCluster(cluster: Any){
        recyclerManager.addCluster(cluster)
    }

    private fun initRecyclerView() {

    }

    open fun resetScrollingState(){
        scrollListener.resetState()
    }

    open fun onScrollingUp(){

    }

    open fun onScrollingDown(){

    }

    open fun onBackingToTop(shouldShow: Boolean){

    }

    open fun onRefresh(){

    }

    override fun hideLoading() {
        super.hideLoading()
        swipeRefresh.isRefreshing = false
    }

    override fun showLoading() {
        super.showLoading()
        swipeRefresh.isRefreshing = true
    }

    fun smoothScrollingToBottom(){
        recyclerView.smoothScrollToPosition(recyclerView.childCount)
        Log.e("smooth to scroll","${recyclerView}")
    }

    open fun shouldLoadMore(): Boolean = true

    open fun shouldPullToRefresh(): Boolean = true

    open fun isReverseLayout() = false
}