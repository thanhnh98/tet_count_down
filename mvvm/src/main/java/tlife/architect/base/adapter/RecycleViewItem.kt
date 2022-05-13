package tlife.architect.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecycleViewItem<VH: RecyclerView.ViewHolder>{
    fun getType(): Int {
        return this.javaClass.hashCode()
    }
    open fun spanSize(): Int = 1
    abstract fun bind(viewHolder: Any)
    abstract fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?
}