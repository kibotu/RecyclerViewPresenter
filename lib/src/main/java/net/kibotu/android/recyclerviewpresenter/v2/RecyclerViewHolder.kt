package net.kibotu.android.recyclerviewpresenter.v2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IBaseViewHolder {

    constructor(@LayoutRes layout: Int, parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    var uuid = UIDGenerator.newUID().toString()

    init {
        log("[create] $uuid")
    }

    override fun onBindViewHolder() {
        log("[onBindViewHolder] $uuid")
    }

    override fun onViewDetachedFromWindow() {
        log("[onViewDetachedFromWindow] $uuid")
    }
}