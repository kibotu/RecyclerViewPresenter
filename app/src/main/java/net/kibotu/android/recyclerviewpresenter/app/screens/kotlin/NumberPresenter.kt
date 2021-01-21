package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R

class NumberPresenter : Presenter<Int>() {

    override val layout = R.layout.number_presenter_item

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<Int>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) = with(viewHolder.itemView) {
        label.text = "$position"
    }
}