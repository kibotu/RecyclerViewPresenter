/**
 * Created by [Jan Rabe](https://kibotu.net).
 */

@file:JvmName("DebugExtensions")

package net.kibotu.android.recyclerviewpresenter

import android.util.Log

internal val debug = BuildConfig.DEBUG

internal fun Any.log(message : String) {
    if (debug)
        Log.d(this::class.java.simpleName, "$message")
}