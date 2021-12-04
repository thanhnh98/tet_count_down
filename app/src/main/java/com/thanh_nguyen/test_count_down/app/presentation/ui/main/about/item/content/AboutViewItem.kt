package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.content

import android.view.View
import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.AboutItemDataModel
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemAboutSectionDataBinding
import com.thanh_nguyen.test_count_down.utils.inflateView

class AboutViewItem(private val aboutItemData: AboutItemDataModel): BindingRecycleViewItem<ItemAboutSectionDataBinding, AboutItemVH>() {
    override fun inflateViewHolder(parent: ViewGroup): AboutItemVH {
        return AboutItemVH(
            inflateView(
                parent,
                R.layout.item_about_section_data
            )
        )
    }

    override fun bindModel(binding: ItemAboutSectionDataBinding?, viewHolder: AboutItemVH) {
        binding?.also {
            it.tvFirstChar.text = aboutItemData.title.subSequence(0, 1)
            it.tvTitle.text = aboutItemData.title.subSequence(1, aboutItemData.title.length)
            it.tvContent.text = aboutItemData.content
            if (aboutItemData.imgDrawable == null)
                it.img.visibility = View.GONE
            else {
                it.img.visibility = View.VISIBLE
                it.img.setImageDrawable(aboutItemData.imgDrawable)
                it.tvImgSource.text = aboutItemData.imgSource
            }
        }
    }
}