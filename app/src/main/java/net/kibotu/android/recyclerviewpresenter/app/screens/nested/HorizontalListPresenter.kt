package net.kibotu.android.recyclerviewpresenter.app.screens.nested

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.RecyclerViewHolder
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv

data class HorizontalListItems(val items: List<Column>)

class HorizontalListPresenter : Presenter<HorizontalListItems>(R.layout.item_horizontal_list) {

    private val View.horizontalList: RecyclerView
        get() = findViewById(R.id.horizontalList)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<HorizontalListItems>, payloads: MutableList<Any>?) {

        logv { "bindViewHolder position=${viewHolder.adapterPosition}" }

        with(viewHolder.itemView) {

            if (horizontalList.adapter == null) {
                horizontalList.setHasFixedSize(true)
                adapter?.recycledViewPool?.let { horizontalList.setRecycledViewPool(it) }
                horizontalList.isNestedScrollingEnabled = false
                logv { "bindViewHolder ${viewHolder.adapterPosition} add adapter" }
                val presenterAdapter = PresenterAdapter()
                presenterAdapter.registerPresenter(ColumnPresenter())
                horizontalList.adapter = presenterAdapter
            }

            (horizontalList.adapter as PresenterAdapter).submitList(item
                .model
                .items
                .map { PresenterViewModel(it, R.layout.item_column) }
                .toMutableList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = object : RecyclerViewHolder(parent, layout) {

        init {
            setIsRecyclable(false)
        }

        override fun onViewAttachedToWindow() {
            super.onViewAttachedToWindow()
            Log.v("HorizontalListPresenter", "onViewAttachedToWindow $adapterPosition")
        }

        override fun onViewDetachedFromWindow() {
            super.onViewDetachedFromWindow()
            Log.v("HorizontalListPresenter", "onViewDetachedFromWindow $adapterPosition remove adapter")
            (itemView.horizontalList.adapter as PresenterAdapter).clear()
        }

        override fun onViewRecycled() {
            super.onViewRecycled()
            Log.v("HorizontalListPresenter", "onViewRecycled $adapterPosition remove adapter")
        }

        override fun onFailedToRecycleView(): Boolean {
            Log.v("HorizontalListPresenter", "onFailedToRecycleView $adapterPosition remove adapter")
            return super.onFailedToRecycleView()
        }
    }

}