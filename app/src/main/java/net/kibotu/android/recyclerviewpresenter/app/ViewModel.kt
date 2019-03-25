package net.kibotu.android.recyclerviewpresenter.app

import java.util.*

data class ViewModel<T>(val t: T, val uuid: String = UUID.randomUUID().toString())