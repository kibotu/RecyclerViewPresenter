package net.kibotu.android.recyclerviewpresenter.v2

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.UIDGenerator

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
data class PresenterViewModel<T>(
    val model: T,
    @LayoutRes
    val layout: Int,
    var uuid: String = UIDGenerator.newUID().toString(),

    /**
     * Hook for diff utils
     *
     * @see https://developer.android.com/reference/android/support/v7/util/DiffUtil.Callback.html#getChangePayload(int,%20int)
     */
    var changedPayload: ((new: T, old: T) -> Bundle?)? = null,

    /**
     * Invokes when view gets attached.
     */
    var onAttachStateChangeListener: View.OnAttachStateChangeListener? = null,

    /**
     * Invokes click listener.
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    var onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null,
) {

    /**
     * Hook for diff utils
     *
     * @see https://developer.android.com/reference/android/support/v7/util/DiffUtil.Callback.html#getChangePayload(int,%20int)
     */
    @Suppress("UNCHECKED_CAST")
    internal fun changedPayload(new: Any): Bundle? = changedPayload?.invoke(new as T, model)

    /**
     * Convenience method to invoke [onItemClickListener].
     */
    fun onClick(viewHolder: RecyclerView.ViewHolder) {
        onItemClickListener?.invoke(model, viewHolder.itemView, viewHolder.adapterPosition)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PresenterViewModel<*>

        if (model != other.model) return false
        if (layout != other.layout) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = model?.hashCode() ?: 0
        result = 31 * result + layout
        result = 31 * result + uuid.hashCode()
        return result
    }

    override fun toString(): String = "PresenterViewModel(model=$model, layout=$layout, uuid='$uuid')"
}