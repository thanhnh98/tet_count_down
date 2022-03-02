package com.thanh_nguyen.test_count_down.app.presentation.dialog.items

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.presentation.dialog.MusicChoosingBottomDialog
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemSingleeMusicBinding
import com.thanh_nguyen.test_count_down.provider.AppProvider
import com.thanh_nguyen.test_count_down.utils.inflateView
import com.thanh_nguyen.test_count_down.utils.onClick

class MusicDefaultItemView(
    private val onSelectedItem: (Int) -> Unit
): BindingRecycleViewItem<ItemSingleeMusicBinding, MusicItemViewHolder>() {
    override fun inflateViewHolder(parent: ViewGroup): MusicItemViewHolder {
        return MusicItemViewHolder(
            inflateView(parent, R.layout.item_singlee_music)
        )
    }

    override fun bindModel(binding: ItemSingleeMusicBinding?, viewHolder: MusicItemViewHolder) {
        binding?.tvName?.text = Constants.DEFAULT_MUSIC_SINGER_NAME
        binding?.tvEditableName?.text = "${Constants.DEFAULT_MUSIC_NAME} (Mặc định)"
        binding?.root?.onClick {
            onSelectedItem.invoke(viewHolder.bindingAdapterPosition)

        }
        binding?.imgCover?.setImageResource(R.mipmap.ic_launcher)

        if (MusicChoosingBottomDialog.musicItemPositionSelected == viewHolder.bindingAdapterPosition){
            binding?.layoutContainer?.background = AppProvider.getDrawable(R.drawable.bg_gradient_corner_selected)
        }
        else
        {
            binding?.layoutContainer?.background = AppProvider.getDrawable(R.drawable.bg_gradient_corner)
        }
    }
}