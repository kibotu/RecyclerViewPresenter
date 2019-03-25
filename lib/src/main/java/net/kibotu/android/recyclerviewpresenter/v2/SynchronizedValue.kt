package net.kibotu.android.recyclerviewpresenter.v2

internal data class SynchronizedValue<T>(private var t: T) {

    private val lock = Any()

    fun set(t: T) {
        synchronized(lock) {
            this.t = t
        }
    }

    fun get(): T {
        synchronized(lock) {
            return t
        }
    }
}