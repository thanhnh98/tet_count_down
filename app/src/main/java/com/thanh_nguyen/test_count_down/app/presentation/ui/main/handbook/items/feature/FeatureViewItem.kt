package com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.feature

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.FeatureItemModel
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemFeatureBinding
import com.thanh_nguyen.test_count_down.utils.inflateView
import com.thanh_nguyen.test_count_down.utils.onClick

class FeatureViewItem(
    private val featureData: FeatureItemModel,
    private val callback: (FeatureItemModel) -> Unit
): BindingRecycleViewItem<ItemFeatureBinding, FeatureVH>() {
    override fun inflateViewHolder(parent: ViewGroup): FeatureVH {
        return FeatureVH(
            inflateView(
                parent,
                R.layout.item_feature
            )
        )
    }

    override fun bindModel(binding: ItemFeatureBinding?, viewHolder: FeatureVH) {
        binding?.root?.onClick {
            callback.invoke(featureData)
        }

        binding?.icFeatureIcon?.setImageDrawable(featureData.icon)
        binding?.tvName?.text = featureData.name
    }
}