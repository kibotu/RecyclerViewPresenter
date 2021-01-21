package net.kibotu.android.recyclerviewpresenter.app.screens.listadapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.PresenterViewModel

class NumberPresenter : Presenter<Int>() {

    override val layout = R.layout.number_presenter_item

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<Int>, payloads: MutableList<Any>?) = with(viewHolder.itemView) {
        label.text = "${viewHolder.adapterPosition}"
    }
}