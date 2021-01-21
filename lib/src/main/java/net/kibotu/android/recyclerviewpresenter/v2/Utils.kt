package net.kibotu.android.recyclerviewpresenter.v2

import androidx.annotation.AnyRes

internal fun Adapter.resName(@AnyRes id: Int): String = recyclerView?.resources?.getResourceEntryName(id) ?: "$id"