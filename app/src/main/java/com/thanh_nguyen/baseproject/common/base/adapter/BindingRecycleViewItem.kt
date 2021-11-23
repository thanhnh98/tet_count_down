package com.thanh_nguyen.baseproject.common.base.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecycleViewItem<DB: ViewDataBinding, VH: RecyclerView.ViewHolder>: RecycleViewItem<VH>() {
    private var binding: DB? = null

    override fun bind(viewHolder: Any) {
        binding = DataBindingUtil.bind((viewHolder as VH).itemView)
        bindModel(binding, viewHolder)
    }

    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return inflateViewHolder(parent)
    }

    abstract fun inflateViewHolder(parent: ViewGroup): VH

    abstract fun bindModel(binding: DB?, viewHolder: VH)
}