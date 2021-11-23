package com.thanh_nguyen.baseproject.common.view.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thanh_nguyen.baseproject.R
import com.thanh_nguyen.baseproject.utils.loadImage
import com.thanh_nguyen.baseproject.utils.onClick

class BannerViewPagerAdapter(
    private val data: List<BannerViewAdapterModel> = emptyList(),
    private val isInfinite: Boolean = false,
    private val onItemClick: ((BannerViewAdapterModel) -> Unit)? = null,
): RecyclerView.Adapter<BannerViewPagerVH>() {
    private var listDataFake: MutableList<BannerViewAdapterModel> = ArrayList()
    init {
        listDataFake.addAll(
            data.subList(0,3)
        )
        if (isInfiniteScrolling()) {
            listDataFake.add(0, listDataFake[listDataFake.size - 1])
            listDataFake.add(0, listDataFake[listDataFake.size - 2])
            listDataFake.add(listDataFake[2])
            listDataFake.add(listDataFake[3])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewPagerVH {
        return BannerViewPagerVH(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_banner, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerViewPagerVH, position: Int) {
        val dataItem = listDataFake[position]

        holder.imgBanner.onClick{
            onItemClick?.invoke(dataItem)
        }

        dataItem.bannerData.apply {
            loadImage(
                url = this?.url?:return,
                holder.imgBanner,
            )
        }
    }

    override fun getItemCount(): Int {
        return listDataFake.size
    }

    fun isInfiniteScrolling() = isInfinite && data.size > 2
}