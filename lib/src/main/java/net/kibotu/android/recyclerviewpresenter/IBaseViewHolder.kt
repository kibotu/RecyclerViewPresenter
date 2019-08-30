package net.kibotu.android.recyclerviewpresenter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface IBaseViewHolder : View.OnAttachStateChangeListener {

    /**
     * [RecyclerView.Adapter.onBindViewHolder]
     */
    @Deprecated("use onViewAttachedToWindow(view:View?)", replaceWith = ReplaceWith("onViewAttachedToWindow(view:View?)"))
    fun onViewAttachedToWindow()

    /**
     * [RecyclerView.Adapter.onViewDetachedFromWindow]
     */
    @Deprecated("use onViewDetachedFromWindow(view:View?)", replaceWith = ReplaceWith("onViewDetachedFromWindow(view:View?)"))
    fun onViewDetachedFromWindow()

    /**
     * [RecyclerView.Adapter.onViewRecycled]
     */
    fun onViewRecycled() {

    }

    override fun onViewAttachedToWindow(view: View?) {
    }

    override fun onViewDetachedFromWindow(view: View?) {
    }

    /**
     * [RecyclerView.Adapter.onFailedToRecycleView]
     */
    fun onFailedToRecycleView(): Boolean = false
}