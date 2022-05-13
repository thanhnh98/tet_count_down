package tlife.architect.base.mvvm.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tlife.architect.base.adapter.RecyclerManager
import tlife.architect.base.mvvm.viewmodel.BaseCollectionViewModel
import tlife.architect.base.EndlessRecyclerViewScrollListener

abstract class BaseCollectionActivityMVVM<DB: ViewDataBinding, VM: BaseCollectionViewModel>: BaseActivityMVVM<DB, VM>() {
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val SPAN_COUNT = 2
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout
    var recyclerManager = RecyclerManager<Any>()
    var gridLayoutManager = GridLayoutManager(this, SPAN_COUNT)


    abstract fun inflateRecyclerView(): RecyclerView
    abstract fun inflateSwipeRefresh(): SwipeRefreshLayout

    override fun onCreateX(savedInstanceState: Bundle?) {
        recyclerView = inflateRecyclerView()
        swipeRefresh = inflateSwipeRefresh()
        addBottomLoadingItem()
        initRecyclerView()
    }

    private fun addBottomLoadingItem() {

    }

    private fun initRecyclerView() {
        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (shouldLoadMore()) {
                    viewModel.invokeLoadMore()
                }
            }
        }
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

    open fun shouldLoadMore(): Boolean = true
}