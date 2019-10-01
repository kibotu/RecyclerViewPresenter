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
 * Implementation of a Circularly-addressable [kotlin.collections.MutableList], allowing negative
 * indexes and positive indexes that are larger than the size of the List.
 */
class MutableCircularList<T>(private val list: MutableList<T>) : MutableList<T> by list {

    /**
     * Add the [element] at the specified [index].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.add
     */
    override fun add(index: Int, element: T) =
        list.add(index.safely(), element)

    /**
     * Add all of the [elements] starting at the specified [index].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.addAll
     */
    override fun addAll(index: Int, elements: Collection<T>): Boolean =
        list.addAll(index.safely(), elements)

    /**
     * Get the element at the specified [index].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.get
     */
    override fun get(index: Int): T =
        list[index.safely()]

    /**
     * Get a [kotlin.collections.ListIterator] starting at the specified [index]
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.listIterator
     */
    override fun listIterator(index: Int): MutableListIterator<T> =
        list.listIterator(index.safely())

    /**
     * Remove the element at the specified [index].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.removeAt
     */
    override fun removeAt(index: Int): T =
        list.removeAt(index.safely())

    /**
     * Replace the existing element at the specified [index] with the given [element].
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.set
     */
    override fun set(index: Int, element: T): T =
        list.set(index.safely(), element)

    /**
     * Get a List bound by [fromIndex] inclusive to [toIndex] exclusive
     *
     * If [fromIndex] or [toIndex] is negative they are interpreted as an offset from the end of
     * the list. If the [fromIndex] or [toIndex] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.MutableCircularList.subList
     */
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {

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
        if (this < 0) (this % lastIndex + lastIndex) % lastIndex
        else this % lastIndex

}