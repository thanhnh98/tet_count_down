package com.thanh_nguyen.baseproject.app.presentation.ui.main.home.item

import android.view.ViewGroup
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.baseproject.common.view.banner.BannerViewAdapterModel
import com.thanh_nguyen.baseproject.common.view.banner.BannerViewPagerAdapter
import com.thanh_nguyen.baseproject.common.view.banner.BannerViewPagerHelper
import com.thanh_nguyen.baseproject.databinding.ItemBannerBinding
import com.thanh_nguyen.baseproject.utils.inflateView
import kotlinx.coroutines.Job

class BannerRecycleViewItem(
        private val listData: List<BannerViewAdapterModel>,
        private val onItemClick: ((BannerViewAdapterModel) -> Unit)? = null
    ): BindingRecycleViewItem<ItemBannerBinding, BannerRecycleVH>() {
    override fun inflateViewHolder(parent: ViewGroup): BannerRecycleVH {
        return BannerRecycleVH(
            inflateView(parent, R.layout.item_banner)
        )
    }

    override fun bindModel(binding: ItemBannerBinding?, viewHolder: BannerRecycleVH) {
        BannerViewPagerHelper(
                viewPager2 = binding?.vp2Banner?:return,
                bannerAdapter = BannerViewPagerAdapter(
                    listData,
                    isInfinite = true,
                    onItemClick
                ))
            .setPageMarginRight(R.dimen._48dp)
            .setPageMarginLeft(R.dimen._48dp)
            .execute()
    }
}