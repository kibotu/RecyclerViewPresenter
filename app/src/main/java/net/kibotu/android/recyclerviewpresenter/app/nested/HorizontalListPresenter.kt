package net.kibotu.android.recyclerviewpresenter.app.nested

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_horizontal_list.view.*
import net.kibotu.android.recyclerviewpresenter.*
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv

data class HorizontalListItems(val items: List<Column>)

class HorizontalListPresenter : Presenter<HorizontalListItems>() {

    override val layout = R.layout.item_horizontal_list

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<HorizontalListItems>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) {

        logv { "bindViewHolder position=$position" }

        with(viewHolder.itemView) {

            if (horizontalList.adapter == null) {
                horizontalList.setHasFixedSize(true)
                horizontalList.setRecycledViewPool(adapter.recycledViewPool)
                horizontalList.isNestedScrollingEnabled = false
                logv { "bindViewHolder $position add adapter" }
                val presenterAdapter = PresenterAdapter()
                presenterAdapter.registerPresenter(ColumnPresenter())
                horizontalList.adapter = presenterAdapter
            }

            (horizontalList.adapter as PresenterAdapter).submitList(item.model.items.map { PresenterModel(it, R.layout.item_column) })
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