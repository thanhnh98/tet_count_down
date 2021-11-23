package com.thanh_nguyen.baseproject.common.view.banner

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.thanh_nguyen.baseproject.R

class BannerViewPagerVH(view: View): RecyclerView.ViewHolder(view){
    val imgBanner: ImageView = view.findViewById(R.id.img_banner)
}