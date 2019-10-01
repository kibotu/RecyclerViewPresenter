package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.DiffUtil

open class PresenterModelDiffCallback(var oldItems: List<PresenterModel<*>>, var newItems: List<PresenterModel<*>>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].uuid == newItems[newItemPosition].uuid

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].model == (newItems[newItemPosition]).model

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return oldItems[oldItemPosition].changedPayload(newItems[newItemPosition].model ?: return super.getChangePayload(oldItemPosition, newItemPosition)) ?: super.getChangePayload(
            oldItemPosition,
            newItemPosition
        )
    }
}