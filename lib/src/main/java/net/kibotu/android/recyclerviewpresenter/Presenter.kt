package net.kibotu.android.recyclerviewpresenter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference


/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
abstract class Presenter<T, B: ViewBinding>(
    /**
     * Used for inflating the view holder layout.
     *
     * @return Layout Resource Id.
     * @return ViewBinding accessor.
     */
    @get:LayoutRes
    val layout: Int,
    private val viewBindingAccessor: (View) -> B
) {

    // region adapter

    private var _adapter: WeakReference<PresenterAdapter>? = null

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
     * @param viewBinding Current [B]
     * @param viewHolder Current [VH].
     * @param item       Current [T]
     * @param payloads Changes
     */
    abstract fun bindViewHolder(
        viewBinding: B,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<T>,
        payloads: MutableList<Any>?
    )

    @Suppress("UNCHECKED_CAST")
    internal fun bindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<*>,
        payloads: MutableList<Any>?
    ) = bindViewHolder(
        viewBindingAccessor.invoke(viewHolder.itemView),
        viewHolder,
        item as PresenterViewModel<T>,
        payloads
    )
}