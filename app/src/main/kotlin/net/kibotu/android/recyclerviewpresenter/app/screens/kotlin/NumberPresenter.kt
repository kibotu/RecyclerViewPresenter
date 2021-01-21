package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R

class NumberPresenter : Presenter<Int>(R.layout.number_presenter_item) {

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<Int>, payloads: MutableList<Any>?) = with(viewHolder.itemView) {
        label.text = "${item.model}"
    }
}