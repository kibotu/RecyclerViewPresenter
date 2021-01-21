package net.kibotu.android.recyclerviewpresenter.cirkle

import org.junit.Test
import kotlin.test.assertEquals


class CircularListTest {

    @Test
    fun get() {

        val given = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')

        val actual = given.circular()

        given.forEachIndexed { index, i ->
            assertEquals(i, actual[index])
        }

        // off by one
        assertEquals(given.last(), actual[-1])
        assertEquals(given.first(), actual[actual.lastIndex + 1])

        // off by size + 1
        assertEquals(given.first(), actual[-(actual.size)])
        assertEquals(given.first(), actual[actual.size])
        assertEquals(given.last(), actual[-(actual.size + 1)])
        assertEquals(given.drop(1).first(), actual[-(actual.size - 1)])
    }

    @Test
    fun listIterator() {
    }

    @Test
    fun subList() {
    }
}