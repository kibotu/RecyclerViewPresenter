package net.kibotu.android.recyclerviewpresenter.v2

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference


/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
abstract class Presenter<T>(
    /**
     * Used for inflating the view holder layout.
     *
     * @return Layout Resource Id.
     */
    @get:LayoutRes
    val layout: Int
) {

    // region adapter

    private var _adapter: WeakReference<PresenterListAdapter>? = null

    /**
     * Reference to the bound [RecyclerView].
     */
    var adapter
        get() = _adapter?.get()
        set(value) {
            _adapter = if (value != null)
                WeakReference(value)
            else
                null
        }

    // endregion

    /**
     * [PresenterAdapter.createViewHolder]
     */
    open fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = RecyclerViewHolder(parent, layout)

    /**
     * Binds [T] to [VH]. Use [.get] to retrieve neighbour [T].
     *
     * @param viewHolder Current [VH].
     * @param item       Current [T]
     * @param payloads Changes
     */
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<T>, payloads: MutableList<Any>?)

    @Suppress("UNCHECKED_CAST")
    internal fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<*>, payloads: MutableList<Any>?) =
        bindViewHolder(viewHolder, item as PresenterViewModel<T>, payloads)
}