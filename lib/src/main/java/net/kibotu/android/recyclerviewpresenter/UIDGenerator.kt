package net.kibotu.android.recyclerviewpresenter

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
internal object UIDGenerator {

    private const val START_UID = 0

    /**
     * https://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/
     */
    private val nextUID = AtomicInteger(START_UID)

    fun newUID(): Int {
        if (!isValid(nextUID.get())) {
            throw IllegalStateException("UID pool depleted")
        }
        return nextUID.incrementAndGet()
    }

    private fun isValid(uid: Int): Boolean = uid >= START_UID
}