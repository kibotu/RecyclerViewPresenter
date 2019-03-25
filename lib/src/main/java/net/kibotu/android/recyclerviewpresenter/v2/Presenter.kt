package net.kibotu.android.recyclerviewpresenter.v2

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class Presenter<T : RecyclerViewModel<*>> {

    /**
     * Respective adapter.
     */
    var adapter: PresenterAdapter<T> by Delegates.notNull()

    /**
     * Used for inflating the view holder layout.
     *
     * @return Layout Resource Id.
     */
    @get:LayoutRes
    abstract val layout: Int

    /**
     * Binds [T] to [VH]. Use [.get] to retrieve neighbour [T].
     *
     * @param viewHolder Current [VH].
     * @param item       Current [T]
     * @param position   Adapter position.
     */
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: T, position: Int)
}