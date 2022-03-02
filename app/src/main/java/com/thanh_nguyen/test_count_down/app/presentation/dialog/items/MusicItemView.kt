package com.thanh_nguyen.test_count_down.app.presentation.dialog.items

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.presentation.dialog.MusicChoosingBottomDialog
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemSingleeMusicBinding
import com.thanh_nguyen.test_count_down.provider.AppProvider
import com.thanh_nguyen.test_count_down.utils.inflateView
import com.thanh_nguyen.test_count_down.utils.onClick

class MusicItemView(
    private val musicData: LocalMusicModel,
    private val onItemSelected: (Int, LocalMusicModel) -> Unit,
): BindingRecycleViewItem<ItemSingleeMusicBinding, MusicItemViewHolder>() {
    override fun inflateViewHolder(parent: ViewGroup): MusicItemViewHolder {
        return MusicItemViewHolder(
            inflateView(parent, R.layout.item_singlee_music)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun bindModel(binding: ItemSingleeMusicBinding?, viewHolder: MusicItemViewHolder) {

        binding?.tvName?.text = musicData.artist
        binding?.tvEditableName?.text = musicData.title
        binding?.root?.onClick {
            onItemSelected.invoke(viewHolder.bindingAdapterPosition, musicData)
        }
        musicData.getThumbnailBitmap()?.apply {
            binding?.imgCover?.setImageBitmap(this)
        }

        if (MusicChoosingBottomDialog.musicItemPositionSelected == viewHolder.bindingAdapterPosition) {
            binding?.layoutContainer?.background = AppProvider.getDrawable(R.drawable.bg_gradient_corner_selected)
        }
        else {
            binding?.layoutContainer?.background = AppProvider.getDrawable(R.drawable.bg_gradient_corner)
        }
    }
}