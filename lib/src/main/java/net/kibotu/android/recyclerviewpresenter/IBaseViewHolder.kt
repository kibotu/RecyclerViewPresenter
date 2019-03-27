package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface IBaseViewHolder {

    /**
     * [RecyclerView.Adapter.onBindViewHolder]
     */
    fun onBindViewHolder()

    /**
     * [RecyclerView.Adapter.onViewDetachedFromWindow]
     */
    fun onViewDetachedFromWindow()
}
