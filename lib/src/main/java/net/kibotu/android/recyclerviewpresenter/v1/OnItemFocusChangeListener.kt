package net.kibotu.android.recyclerviewpresenter.v1

import android.view.View

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface OnItemFocusChangeListener<T> {

    /**
     * Invokes click listener.
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    fun onFocusChange(item: T, view: View, hasFocus: Boolean)
}