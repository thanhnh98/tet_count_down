package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items

import android.view.View
import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemSingleeMusicBinding
import com.thanh_nguyen.test_count_down.utils.inflateView
import com.thanh_nguyen.test_count_down.utils.onClick

class MusicDefaultItemView(
    private val onSelectedItem: () -> Unit
): BindingRecycleViewItem<ItemSingleeMusicBinding, MusicItemViewHolder>() {
    override fun inflateViewHolder(parent: ViewGroup): MusicItemViewHolder {
        return MusicItemViewHolder(
            inflateView(parent, R.layout.item_singlee_music)
        )
    }

    override fun bindModel(binding: ItemSingleeMusicBinding?, viewHolder: MusicItemViewHolder) {
        binding?.tvName?.text = "Mặc định"
        binding?.tvEditableName?.text = "Happy New Year - N/A"
        binding?.imgRemove?.visibility = View.INVISIBLE
        binding?.root?.onClick {
            onSelectedItem.invoke()
        }
    }
}