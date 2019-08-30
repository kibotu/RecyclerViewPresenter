package net.kibotu.android.recyclerviewpresenter.app.nested

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_horizontal_list.view.*
import net.kibotu.android.recyclerviewpresenter.*
import net.kibotu.android.recyclerviewpresenter.app.R

data class HorizontalListItems(val items: List<Column>)

class HorizontalListPresenter : Presenter<HorizontalListItems>() {

    override val layout = R.layout.item_horizontal_list

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<HorizontalListItems>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) {

        with(viewHolder.itemView) {

            if (horizontalList.adapter == null) {
                val presenterAdapter = PresenterAdapter()
                presenterAdapter.registerPresenter(ColumnPresenter())
                horizontalList.adapter = presenterAdapter
            }

            (horizontalList.adapter as PresenterAdapter).submitList(item.model.items.map { PresenterModel(it, R.layout.item_column) })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = object : RecyclerViewHolder(parent, layout) {

        override fun onViewAttachedToWindow(view: View?) {
            super.onViewAttachedToWindow(view)
            Log.v("RecyclerViewHolder", "onViewAttachedToWindow")
        }

        override fun onViewDetachedFromWindow(view: View?) {
            super.onViewDetachedFromWindow(view)
            Log.v("RecyclerViewHolder", "onViewDetachedFromWindow")
            (itemView.horizontalList.adapter as PresenterAdapter).clear()
            itemView.horizontalList.adapter = null
        }

        override fun onViewRecycled() {
            super.onViewRecycled()
            Log.v("RecyclerViewHolder", "onViewRecycled")
        }

        override fun onFailedToRecycleView(): Boolean {
            Log.v("RecyclerViewHolder", "onFailedToRecycleView")
            return super.onFailedToRecycleView()
        }
    }

}