package tlife.architect.base.mvvm.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tlife.architect.base.mvvm.viewmodel.BaseCollectionViewModel
import tlife.architect.base.EndlessRecyclerViewScrollListener
import tlife.architect.base.adapter.RecyclerManager


abstract class BaseCollectionFragmentMVVM<DB: ViewDataBinding, VM: BaseCollectionViewModel> : BaseFragmentMVVM<DB, VM>() {
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var isLoadingMore = false
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout
    var recyclerManager = RecyclerManager<Any>()
    var gridLayoutManager = GridLayoutManager(context, getSpanCount())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClusters()
    }

    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)
        recyclerView = inflateRecyclerView()
        swipeRefresh = inflateSwipeRefresh()
        setupRecyclerView()
    }

    abstract fun inflateRecyclerView(): RecyclerView
    abstract fun inflateSwipeRefresh(): SwipeRefreshLayout

    private fun setupRecyclerView() {
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (shouldLoadMore()) {
                    viewModel.invokeLoadMore()
                }
            }
        }

        with(gridLayoutManager){
            reverseLayout = isReverseLayout()
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return recyclerManager.adapter.getItemViewSpanSize(position)
                }
            }
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
//            it.setColorSchemeColors(
//                ContextCompat.getColor(
//                    requireContext(),
//                    R.color.purple_200
//                )
//            )
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
    }

    open fun shouldLoadMore(): Boolean = true

    open fun shouldPullToRefresh(): Boolean = true

    open fun isReverseLayout() = false

    open fun getSpanCount(): Int = 1
}