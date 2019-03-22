package net.kibotu.android.recyclerviewpresenter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IBaseViewHolder {

    constructor(@LayoutRes layout: Int, parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    private val TAG = BaseViewHolder::class.java.simpleName

    var uuid = UIDGenerator.newUID().toString()

    var debug = true

    init {
        if (debug)
            Log.v(TAG, "[create] $uuid")
    }

    override fun onBindViewHolder() {
        if (debug)
            Log.v(TAG, "[onBindViewHolder] $uuid")
    }

    override fun onViewDetachedFromWindow() {
        if (debug)
            Log.v(TAG, "[onViewDetachedFromWindow] $uuid")
    }
}