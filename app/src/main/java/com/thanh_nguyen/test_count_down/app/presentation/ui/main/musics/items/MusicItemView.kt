package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics.items

import android.view.ViewGroup
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.common.base.adapter.BindingRecycleViewItem
import com.thanh_nguyen.test_count_down.databinding.ItemSingleeMusicBinding
import com.thanh_nguyen.test_count_down.utils.inflateView
import com.thanh_nguyen.test_count_down.utils.onClick

class MusicItemView(
    private val musicData: LocalMusicModel,
    private val onItemSelected: (LocalMusicModel) -> Unit,
): BindingRecycleViewItem<ItemSingleeMusicBinding, MusicItemViewHolder>() {
    override fun inflateViewHolder(parent: ViewGroup): MusicItemViewHolder {
        return MusicItemViewHolder(
            inflateView(parent, R.layout.item_singlee_music)
        )
    }

    override fun bindModel(binding: ItemSingleeMusicBinding?, viewHolder: MusicItemViewHolder) {
        binding?.tvName?.text = musicData.artist
        binding?.tvEditableName?.text = musicData.title
        binding?.root?.onClick {
            onItemSelected.invoke(musicData)
        }
        musicData.thumbnail?.apply {
            binding?.imgCover?.setImageBitmap(this)
        }
    }
}