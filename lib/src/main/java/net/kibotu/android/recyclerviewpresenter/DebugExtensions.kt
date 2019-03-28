/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

@file:JvmName("DebugExtensions")

package net.kibotu.android.recyclerviewpresenter

import android.util.Log

internal val debug = false

internal fun Any.log(message: String?) {
    if (debug)
        Log.d(this::class.java.simpleName, "$message")
}

internal fun Exception.log(message: String?) {
    if (debug)
        Log.d(this::class.java.simpleName, "$message")
}