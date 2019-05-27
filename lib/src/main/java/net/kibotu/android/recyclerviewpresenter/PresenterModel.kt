package net.kibotu.android.recyclerviewpresenter

import android.view.View
import androidx.annotation.LayoutRes

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
open class PresenterModel<T>(
    val model: T,
    @LayoutRes
    val layout: Int,
    val uuid: String = UIDGenerator.newUID().toString(),
    /**
     * Invokes click listener.
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    var onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
)