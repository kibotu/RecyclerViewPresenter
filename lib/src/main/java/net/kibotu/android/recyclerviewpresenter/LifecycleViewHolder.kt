package net.kibotu.android.recyclerviewpresenter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList


abstract class LifecycleViewHolder(parent: ViewGroup, layout: Int) : RecyclerViewHolder(parent, layout), LifecycleOwner {

    var lifecycleRegistry: LifecycleRegistry

    init {
        log { "init LifecycleViewHolder" }
        lifecycleRegistry = LifecycleRegistry(this)
    }

    override fun onViewAttachedToWindow() {
        log { "onViewAttachedToWindow" }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onViewDetachedFromWindow() {
        log { "onViewDetachedFromWindow" }
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}

inline fun <reified T> LifecycleViewHolder.submitList(adapter: PresenterPageListAdapter<T>, items: List<PresenterModel<T>>, isCircular: Boolean = false, config: PagedList.Config? = null) {

    if (items.isEmpty())
        return

    val dataSourceFactory = if (isCircular)
        CircularDataSource.Factory(items)
    else
        ListDataSource.Factory(items)

    val dataSourceSource = LivePagedListBuilder(
        dataSourceFactory, config ?: PagedList.Config.Builder()
            .setPageSize(10.coerceAtMost(items.size))
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()
    ).build()

    dataSourceSource.observe(this, Observer {
        adapter.submitList(it)
    })
}