package net.kibotu.android.recyclerviewpresenter

import androidx.annotation.AnyRes

internal fun Adapter.resName(@AnyRes id: Int): String = recyclerView?.resources?.getResourceEntryName(id) ?: "$id"

internal inline fun <reified T> List<T>.circularPosition(position: Int): Int =
    if (position < 0) (position % lastIndex + lastIndex) % lastIndex
    else position % lastIndex

internal inline fun <reified T> List<T>.getCircular(position: Int): T = this[circularPosition(position)]