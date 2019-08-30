/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

@file:JvmName("DebugExtensions")

package net.kibotu.android.recyclerviewpresenter

import android.util.Log

internal val debug = false

internal fun Any.log(block: () -> String?) {
    if (debug)
        Log.d(this::class.java.simpleName, "${block()}")
}

internal fun Exception.log(block: () -> String?) {
    if (debug)
        Log.d(this::class.java.simpleName, "${block()}")
}