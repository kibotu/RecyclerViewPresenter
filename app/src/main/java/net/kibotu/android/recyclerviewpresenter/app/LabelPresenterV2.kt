package net.kibotu.android.recyclerviewpresenter.app

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.RecyclerViewModel

class LabelPresenterV2 : Presenter<RecyclerViewModel<String>>() {

    override val layout: Int = R.layout.label_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {
        label.text = item.model
    }
}