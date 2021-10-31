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

    val uuid by lazy { UIDGenerator.newUID().toString().substring(0, 4) }
}
