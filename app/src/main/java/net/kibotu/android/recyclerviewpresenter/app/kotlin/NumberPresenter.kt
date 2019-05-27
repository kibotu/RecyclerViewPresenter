package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.number_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R

class NumberPresenter : Presenter<Int>() {

    override val layout = R.layout.number_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<Int>, position: Int) = with(viewHolder.itemView) {
        label.text = "$position"
    }
}