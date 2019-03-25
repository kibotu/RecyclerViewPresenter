package net.kibotu.android.recyclerviewpresenter

import android.view.View

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface OnItemClickListener<T> {

    /**
     * Invokes click listener.
     *
     * @param item     Model of the adapter.
     * @param rowView  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    fun onItemClick(item: T, rowView: View, position: Int)
}