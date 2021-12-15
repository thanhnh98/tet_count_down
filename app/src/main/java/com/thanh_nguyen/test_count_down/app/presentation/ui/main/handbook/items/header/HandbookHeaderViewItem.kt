package com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.header

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.AboutHeaderDataModel
import com.thanh_nguyen.test_count_down.app.model.HandbookHeaderModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.header.HandbookHeaderVH
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemAboutHeaderBinding
import com.thanh_nguyen.test_count_down.databinding.ItemHandbookHeaderBinding
import com.thanh_nguyen.test_count_down.utils.inflateView

class HandbookHeaderViewItem(private val aboutDataHeader: HandbookHeaderModel): BindingRecycleViewItem<ItemHandbookHeaderBinding, HandbookHeaderVH>() {
    override fun inflateViewHolder(parent: ViewGroup): HandbookHeaderVH {
        return HandbookHeaderVH(
            inflateView(
                parent,
                R.layout.item_handbook_header
            )
        )
    }

    override fun bindModel(binding: ItemHandbookHeaderBinding?, viewHolder: HandbookHeaderVH) {
        binding?.headerData = aboutDataHeader
    }

    override fun spanSize(): Int {
        return 3
    }
}