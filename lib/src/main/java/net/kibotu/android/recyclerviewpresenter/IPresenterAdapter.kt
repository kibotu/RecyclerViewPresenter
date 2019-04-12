package net.kibotu.android.recyclerviewpresenter

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
interface IPresenterAdapter<T> {

    /**
     * Adds [item] with its [clazz] at [position].
     *
     * @param position [RecyclerView.Adapter] position.
     *
     * @param item     [T] as model.
     * @param clazz Concrete [Presenter] type.
     */
    fun <P : Presenter<*>> add(position: Int = 0, item: T, clazz: Class<P>)

    /**
     * Adds [item] with its [clazz] at the beginning of the list.
     *
     * @param item     [T] as model.
     * @param clazz Concrete [Presenter] type.
     */
    fun <P : Presenter<*>> prepend(item: T, clazz: Class<P>)

    /**
     * Adds [item] with its [clazz] at the end of the list.
     *
     * @param item     [T] as model.
     * @param clazz Concrete [Presenter] type.
     */
    fun <P : Presenter<*>> append(item: T, clazz: Class<P>)

    /**
     * Clears the adapter and also removes cached views.
     * This is necessary otherwise different layouts will explode if you try to bind them to the wrong {@link RecyclerView.ViewHolder}.
     */
    fun clear()

    /**
     * Clears the adapter and also removes cached views.
     * This is necessary otherwise different layouts will explode if you try to bind them to the wrong {@link RecyclerView.ViewHolder}.
     */
    fun removeAllViews()

    /**
     * Returns  if adapter contains {@link T}
     *
     * @param item {@link T}
     * @return <code>true</code> if contained.
     */
    fun contains(item: T): Boolean

    /**
     * Returns  if adapter contains {@link T}
     *
     * @param item {@link T}
     * @return <code>true</code> if contained.
     */
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

    /**
     * Updates a model {@link T} at adapter position.
     *
     * @param position Adapter position.
     * @param item     {@link T}
     */
    fun <P : Presenter<*>> update(position: Int, item: T, clazz: Class<P>)

    /**
     * Updates a model {@link T} at adapter position.
     *
     * @param position Adapter position.
     * @param item     {@link T}
     */
    fun <P : Presenter<*>> update(position: Int, item: T, clazz: Class<P>, notify: Boolean = false)

    /**
     * Remove an item at adapter position.
     *
     * @param position Adapter position.
     */
    fun remove(position: Int)

    /**
     * Remove an item at adapter position.
     *
     * @param position Adapter position.
     */
    fun remove(position: Int, notify: Boolean = false)
}