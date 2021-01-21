/*
 * MIT License
 *
 * Copyright (c) 2017 Todd Ginsberg
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package net.kibotu.android.recyclerviewpresenter.cirkle

import kotlin.math.absoluteValue

/**
 * Implementation of a Circularly-addressable [kotlin.collections.List], allowing negative
 * indexes and positive indexes that are larger than the size of the List.
 */
class CircularList<out T>(private val list: List<T>) : List<T> by list {
    /**
     * Get the value at the specified [index].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.get
     */
    override operator fun get(index: Int): T =
        list[index.safely()]

    /**
     * Get a [kotlin.collections.ListIterator] starting at the specified [index]
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.listIterator
     */
    override fun listIterator(index: Int): ListIterator<T> =
        list.listIterator(index.safely())

    /**
     * Get a List bound by [fromIndex] inclusive to [toIndex] exclusive
     *
     * If [fromIndex] or [toIndex] is negative they are interpreted as an offset from the end of
     * the list. If the [fromIndex] or [toIndex] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.subList
     */
    override fun subList(fromIndex: Int, toIndex: Int): List<T> {

        val result = mutableListOf<T>()

        var rest = (toIndex - fromIndex).absoluteValue

        val list = if (toIndex < fromIndex)
            list.asReversed()
        else
            list

        while (rest > 0) {

            if (rest > list.size) {
                result.addAll(list.subList(0, list.size))
                rest -= list.size
            } else {
                result.addAll(list.subList(0, rest))
                rest = 0
            }
        }

        return result
    }

    /**
     * Returns a String representation of the object.
     */
    override fun toString(): String =
        list.toString()

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size
}