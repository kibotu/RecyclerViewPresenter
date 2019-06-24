package net.kibotu.android.recyclerviewpresenter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


abstract class LifecycleViewHolder(parent: ViewGroup, layout: Int) : RecyclerViewHolder(parent, layout), LifecycleOwner {

    var lifecycleRegistry: LifecycleRegistry

    init {
        log { "init LifecycleViewHolder" }
        lifecycleRegistry = LifecycleRegistry(this)
    }

    override fun onViewAttachedToWindow() {
        super.onViewAttachedToWindow()
        log { "onViewAttachedToWindow" }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onViewDetachedFromWindow() {
        super.onViewDetachedFromWindow()
        log { "onViewDetachedFromWindow" }
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}