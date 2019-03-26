package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.v1.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import java.util.*

interface IPresenterAdapter<T> {

    /**
     * Adds [item] with its [clazz] at [position].
     *
     * @param position [RecyclerView.Adapter] position.
     *
     * @param item     [T] as model.
     * @param clazz Concrete [Presenter] type.
     */
    fun <P : Presenter<T>> add(position: Int, item: T, clazz: Class<P>)

    fun <P : Presenter<T>> prepend(item: T, clazz: Class<P>)

    fun <P : Presenter<T>> append(item: T, clazz: Class<P>)

    fun <P : Presenter<T>> getPresenterAt(position: Int): P

    /**
     * Clears the adapter and also removes cached views.
     * This is necessary otherwise different layouts will explode if you try to bind them to the wrong {@link RecyclerView.ViewHolder}.
     */
    fun clear()

    fun removeAllViews()

    fun contains(item: T): Boolean

    fun contains(item: T, comparator: (first: T, second: T) -> Boolean): Boolean

    /**
     * Returns [T] at adapter position.
     *
     * @param position Adapter position.
     * @return [T]
     */
    operator fun get(position: Int): T?

    /**
     * Retrieves [T] by using a [Comparator<T>]
     *
     * @param item       [T]
     * @param comparator Filter criteria.
     * @return First [T].
     */
    fun get(item: T, comparator: (first: T, second: T) -> Boolean): T?


    /**
     * Returns adapter position of {@link T}.
     *
     * @param item {@link T}
     * @return <code>-1</code> if not contained.
     */
    fun position(item: T): Int

    /**
     * Returns adapter position of {@link T}.
     *
     * @param item       {@link T}
     * @param comparator Filter criteria.
     * @return <code>-1</code> if not contained.
     */
    fun position(item: T, comparator: (first: T, second: T) -> Boolean): Int

    fun update(position: Int, item: T)

    fun update(position: Int, item: T, notify: Boolean = false)

    fun remove(position: Int)

    fun remove(position: Int, notify: Boolean = false)

    fun sortBy(comparator: Comparator<T>)

    fun <T : Comparable<*>> sort(adapter: PresenterAdapter<T>)
}