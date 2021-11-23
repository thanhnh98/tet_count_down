package com.thanh_nguyen.baseproject.common.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecycleViewAdapter<VH: RecyclerView.ViewHolder, IVH: RecycleViewItem<out VH>> : RecyclerView.Adapter<VH>() {
    private var mItems: MutableList<IVH>
    private val mPrototypeItem: MutableMap<Int, IVH>

    override fun onBindViewHolder(holder: VH, position: Int) {
        mItems[position].bind(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return mPrototypeItem[viewType]?.createViewHolder(parent, viewType) as VH
    }

    val adapter: RecyclerView.Adapter<*>
        get() = this

    override fun getItemViewType(position: Int): Int {
        return mItems[position].getType()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun append(item: IVH) {
        append(mItems.size, item)
    }

    fun append(position: Int, item: IVH) {
        mItems.add(position, item)
        addPrototypeItem(item)
        notifyItemInserted(position)
    }

    fun append(items: List<IVH>) {
        append(mItems.size, items)
    }

    fun append(position: Int, items: List<IVH>) {
        mItems.addAll(position, items)
        addPrototypeItem(items)
        notifyItemRangeInserted(position, items.size)
    }

    fun remove(position: Int) {
        validPosition(position)
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(position: Int, length: Int) {
        if (length == 1) {
            remove(position)
        } else {
            val items: MutableList<IVH> = ArrayList()
            for (i in 0 until mItems.size) {
                if (i < position || i >= position + length) items.add(mItems[i])
            }
            mItems = items
            notifyItemRangeRemoved(position, length)
        }
    }

    fun replace(position: Int, item: IVH) {
        validPosition(position)
        mItems.removeAt(position)
        mItems.add(position, item)
        addPrototypeItem(item)
        notifyItemChanged(position)
    }

    fun refresh(items: MutableList<IVH>) {
        mItems = items
        mPrototypeItem.clear()
        addPrototypeItem(items)
        notifyDataSetChanged()
    }

    val length: Int
        get() = mItems.size

    private fun addPrototypeItem(item: IVH) {
        if (!mPrototypeItem.containsKey(item.hashCode())) {
            mPrototypeItem[item.getType()] = item
        }
    }

    private fun addPrototypeItem(items: List<IVH>) {
        for (item in items) {
            if (!mPrototypeItem.containsKey(item.hashCode())) {
                mPrototypeItem[item.getType()] = item
            }
        }
    }

    fun validPosition(position: Int) {
        check(!(position < 0 || position > mItems.size)) {
            String.format(
                "Recycler-Adapter: Out off range: " +
                        "pos %s length ", position, mItems.size
            )
        }
    }

    init {
        mItems = ArrayList()
        mPrototypeItem = HashMap<Int, IVH>()
    }
}