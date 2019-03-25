package net.kibotu.android.recyclerviewpresenter.v2

internal object UIDGenerator {

    val START_UID = 0
    val INVALID_UID = START_UID - 1

    private val nextUID: SynchronizedValue<Int> = SynchronizedValue(INVALID_UID)

    init {
        nextUID.set(0)
    }

    fun newUID(): Int {
        if (!isValid(nextUID.get())) {
            throw IllegalStateException("UID pool depleted")
        }
        nextUID.set(nextUID.get().plus(1))
        return nextUID.get()
    }

    private fun isValid(uid: Int): Boolean = uid >= START_UID
}