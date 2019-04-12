package net.kibotu.android.recyclerviewpresenter

import android.view.View

class PresenterAdapter<T> : RecyclerViewModelPresenterAdapter<RecyclerViewModel<T>>() {

    fun <P : Presenter<*>> append(
        item: T,
        clazz: Class<P>,
        onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
    ): RecyclerViewModel<T> {
        val recyclerViewModel = RecyclerViewModel(item, onItemClickListener = onItemClickListener)
        append(recyclerViewModel, clazz)
        return recyclerViewModel
    }

    fun <P : Presenter<*>> prepend(
        item: T,
        clazz: Class<P>,
        onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
    ): RecyclerViewModel<T> {
        val recyclerViewModel = RecyclerViewModel(item, onItemClickListener = onItemClickListener)
        prepend(recyclerViewModel, clazz)
        return recyclerViewModel
    }

    fun <P : Presenter<*>> add(
        position: Int = 0,
        item: T,
        clazz: Class<P>,
        onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
    ): RecyclerViewModel<T> {
        val recyclerViewModel = RecyclerViewModel(item, onItemClickListener = onItemClickListener)
        add(position, recyclerViewModel, clazz)
        return recyclerViewModel
    }

    fun <P : Presenter<*>> update(
        position: Int = 0,
        item: T,
        clazz: Class<P>,
        notify: Boolean = false,
        onItemClickListener: ((item: T, view: View, position: Int) -> Unit)? = null
    ): RecyclerViewModel<T> {
        val recyclerViewModel = RecyclerViewModel(item, onItemClickListener = onItemClickListener)
        update(position, recyclerViewModel, clazz, notify)
        return recyclerViewModel
    }
}