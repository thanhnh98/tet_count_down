package com.thanh_nguyen.baseproject.common.view.banner

import android.util.Log
import androidx.annotation.DimenRes
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.okxe.app.util.getScreenWidth
import com.thanh_nguyen.baseproject.App
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.utils.cmn
import kotlinx.coroutines.*

class BannerViewPagerHelper(
    private val viewPager2: ViewPager2,
    private val bannerAdapter: BannerViewPagerAdapter,
    private val defaultPage: Int = 0,
    private val onPageChanged: ((currentPosition: Int) -> Unit)? = null
    ){
    private var pageMarginRight = App.getResources().getDimension(R.dimen.banner_spacing)
    private var pageMarginLeft = App.getResources().getDimension(R.dimen.banner_margin_offset)
    private val offscreen = 3
    private val remainRightSpace = getScreenWidth().toFloat() - App.getResources().getDimension(R.dimen.banner_width)
    private var isIdling = true
    private var currentPosition = 0
    private val pageChangeCallBack = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when(state){
                ViewPager2.SCROLL_STATE_IDLE -> {
                    isIdling = true
                    if (bannerAdapter.isInfiniteScrolling()) {

                        registerCallback()
                        val positionNext: Int = when (currentPosition) {
                            1 -> {
                                bannerAdapter.itemCount - 3
                            }
                            bannerAdapter.itemCount - 2 -> {
                                2
                            }
                            else -> {
                                currentPosition
                            }
                        }
                        viewPager2.setCurrentItem(positionNext, false)
                        onPageChanged?.invoke(positionNext)
                        unregisterCallback()
                    }
                    else {
                        viewPager2.setCurrentItem(currentPosition, false)
                        onPageChanged?.invoke(currentPosition)
                    }
                }
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    isIdling = false
                }
            }
        }
    }

    fun execute(){
        val nextPagePreShow = remainRightSpace - pageMarginRight
        with(viewPager2){
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = bannerAdapter
            offscreenPageLimit = offscreen
            setPageTransformer { page, position ->
                val myOffset: Float = calculatePageMarginOffset(
                    position = position,
                    marginLeft = pageMarginLeft,
                    marginRight = nextPagePreShow,
                )
                if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -myOffset
                    } else {
                        page.translationX = myOffset
                    }
                } else {
                    page.translationY = myOffset
                }
            }
            registerCallback()
            val defaultPageCalculated = if (bannerAdapter.isInfiniteScrolling()) defaultPage + 2 else defaultPage
            if (defaultPageCalculated in 0 until bannerAdapter.itemCount){
                viewPager2.setCurrentItem(defaultPageCalculated, false)
            }
        }
    }

    private fun registerCallback(){
        viewPager2.registerOnPageChangeCallback(pageChangeCallBack)
    }

    private fun unregisterCallback(){
        viewPager2.unregisterOnPageChangeCallback(pageChangeCallBack)
    }

    private fun calculatePageMarginOffset(position: Float, marginLeft: Float, marginRight: Float): Float{
        return marginLeft + (position + 1) * -marginRight
    }

    /**
     * @param dpId: dimension id
     */
    fun setPageMarginRight(@DimenRes dpId: Int): BannerViewPagerHelper {
        pageMarginRight = remainRightSpace - App.getResources().getDimension(dpId)
        return this
    }

    /**
     * @param dpId: dimension id
     */
    fun setPageMarginLeft(@DimenRes dpId: Int): BannerViewPagerHelper {
        pageMarginLeft = App.getResources().getDimension(dpId)
        return this
    }

    fun enableAutoScrolling(autoScrollJob: Job, interval: Long): BannerViewPagerHelper {
        GlobalScope.launch(Dispatchers.IO + autoScrollJob) {
            delay(interval)
            cmn("is isIdling: $isIdling")
            if (isIdling) {
                with(Dispatchers.Main){
                    viewPager2.setCurrentItem(currentPosition + 1, true)
                }
            }
            enableAutoScrolling(autoScrollJob, interval)
        }
        return this
    }
}