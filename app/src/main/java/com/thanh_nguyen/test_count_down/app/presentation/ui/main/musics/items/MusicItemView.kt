package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemSingleeMusicBinding
import com.thanh_nguyen.test_count_down.utils.inflateView

class MusicItemView(
    private val musicData: MusicModel,
    private val onItemSelected: (MusicModel) -> Unit
): BindingRecycleViewItem<ItemSingleeMusicBinding, MusicItemViewHolder>() {
    override fun inflateViewHolder(parent: ViewGroup): MusicItemViewHolder {
        return MusicItemViewHolder(
            inflateView(parent, R.layout.item_singlee_music)
        )
    }

    override fun bindModel(binding: ItemSingleeMusicBinding?, viewHolder: MusicItemViewHolder) {
        binding?.tvName?.text = musicData.name
    }
}