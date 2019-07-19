package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

interface Adapter {

    var recyclerView: RecyclerView?

    val recycledViewPool: RecyclerView.RecycledViewPool?
        get() = recyclerView?.recycledViewPool
}
