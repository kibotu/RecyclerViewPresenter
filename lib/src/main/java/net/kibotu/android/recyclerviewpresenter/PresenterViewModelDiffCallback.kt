package net.kibotu.android.recyclerviewpresenter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

open class PresenterViewModelDiffCallback : DiffUtil.ItemCallback<PresenterViewModel<*>>() {

    override fun areItemsTheSame(oldItem: PresenterViewModel<*>, newItem: PresenterViewModel<*>): Boolean = oldItem.uuid == newItem.uuid

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PresenterViewModel<*>, newItem: PresenterViewModel<*>): Boolean = oldItem.model == newItem.model

    override fun getChangePayload(oldItem: PresenterViewModel<*>, newItem: PresenterViewModel<*>): Any? = oldItem.changedPayload(requireNotNull(newItem.model))
}