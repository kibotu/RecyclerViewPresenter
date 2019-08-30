package net.kibotu.android.recyclerviewpresenter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
abstract class Presenter<T> {

    /**
     * Used for inflating the view holder layout.
     *
     * @return Layout Resource Id.
     */
    @get:LayoutRes
    abstract val layout: Int

    /**
     * [PresenterAdapter.createViewHolder]
     */
    open fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = RecyclerViewHolder(parent, layout)

    /**
     * Binds [T] to [VH]. Use [.get] to retrieve neighbour [T].
     *
     * @param viewHolder Current [VH].
     * @param item       Current [T]
     * @param position   Adapter position.
     * @param payloads Changes
     * @param adapter Adapter
     */
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<T>, position: Int, payloads: MutableList<Any>?, adapter: Adapter)

    @Suppress("UNCHECKED_CAST")
    internal fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<*>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) =
        bindViewHolder(viewHolder, item as PresenterModel<T>, position, payloads, adapter)


}