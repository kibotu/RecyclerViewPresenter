package net.kibotu.android.recyclerviewpresenter.v2

import androidx.annotation.AnyRes
import androidx.recyclerview.widget.RecyclerView

interface Adapter {

    var recyclerView: RecyclerView?

    val recycledViewPool: RecyclerView.RecycledViewPool?
        get() = recyclerView?.recycledViewPool
}
