package net.kibotu.android.recyclerviewpresenter

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
data class PresenterModel<T>(
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
     * Invokes click listener.
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    var onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
) {
    /**
     * Hook for diff utils
     *
     * @see https://developer.android.com/reference/android/support/v7/util/DiffUtil.Callback.html#getChangePayload(int,%20int)
     */
    internal fun changedPayload(new: Any) = changedPayload?.invoke(new as T, model)

    var onAttachStateChangeListener: View.OnAttachStateChangeListener? = null
}