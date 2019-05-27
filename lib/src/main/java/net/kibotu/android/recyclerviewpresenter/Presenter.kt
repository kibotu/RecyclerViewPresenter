package net.kibotu.android.recyclerviewpresenter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
abstract class Presenter<T> {

    /**
     * Injected Respective adapter.
     */
    var adapter: PresenterPageListAdapter<T> by Delegates.notNull()

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
     */
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<T>, position: Int)

    @Suppress("UNCHECKED_CAST")
    internal fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<*>, position: Int) = bindViewHolder(viewHolder, item as PresenterModel<T>, position)
}