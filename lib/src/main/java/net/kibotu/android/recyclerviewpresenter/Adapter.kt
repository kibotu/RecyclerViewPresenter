package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

interface Adapter {

    var recyclerView: WeakReference<RecyclerView>?

    val recycledViewPool: RecyclerView.RecycledViewPool?
        get() = recyclerView?.get()?.recycledViewPool
}
