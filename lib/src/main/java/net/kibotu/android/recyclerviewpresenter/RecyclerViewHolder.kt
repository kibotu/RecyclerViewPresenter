package net.kibotu.android.recyclerviewpresenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
open class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IBaseViewHolder {

    constructor(parent: ViewGroup, @LayoutRes layout: Int) : this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    var uuid = UIDGenerator.newUID().toString()

    init {
        log { "[create] $uuid" }
    }

    override fun onViewAttachedToWindow() {
        log { "[onViewAttachedToWindow] $uuid $adapterPosition" }
    }

    override fun onViewDetachedFromWindow() {
        log { "[onViewDetachedFromWindow] $uuid $adapterPosition" }
    }

    override fun onViewRecycled() {
        log { "[onViewRecycled] $uuid $adapterPosition" }
        super.onViewRecycled()
    }

    override fun onFailedToRecycleView(): Boolean {
        val onFailedToRecycleView = super.onFailedToRecycleView()
        log { "[onFailedToRecycleView] $uuid $onFailedToRecycleView  $adapterPosition" }
        return onFailedToRecycleView
    }
}