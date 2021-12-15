package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.header

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.AboutHeaderDataModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.header.HandbookHeaderVH
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemAboutHeaderBinding
import com.thanh_nguyen.test_count_down.utils.inflateView

class AboutHeaderViewItem(private val aboutDataHeader: AboutHeaderDataModel): BindingRecycleViewItem<ItemAboutHeaderBinding, HandbookHeaderVH>() {
    override fun inflateViewHolder(parent: ViewGroup): HandbookHeaderVH {
        return HandbookHeaderVH(
            inflateView(
                parent,
                R.layout.item_about_header
            )
        )
    }

    override fun bindModel(binding: ItemAboutHeaderBinding?, viewHolder: HandbookHeaderVH) {
        binding?.headerData = aboutDataHeader
    }
}