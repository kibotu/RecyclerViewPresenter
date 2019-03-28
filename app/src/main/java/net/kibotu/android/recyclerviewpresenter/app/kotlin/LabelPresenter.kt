package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.R

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class LabelPresenter : Presenter<RecyclerViewModel<String>>() {

    override val layout: Int = R.layout.label_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {
        label.text = item.model
    }
}