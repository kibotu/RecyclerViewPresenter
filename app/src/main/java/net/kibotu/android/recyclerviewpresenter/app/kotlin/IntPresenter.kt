package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.R

class IntPresenter : Presenter<RecyclerViewModel<Int>>() {

    override val layout: Int = R.layout.label_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<Int>, position: Int) = with(viewHolder.itemView) {
        label.text = "${item.model}"
    }
}