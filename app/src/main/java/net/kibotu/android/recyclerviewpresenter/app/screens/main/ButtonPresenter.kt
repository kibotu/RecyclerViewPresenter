package net.kibotu.android.recyclerviewpresenter.app.screens.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exozet.android.core.extensions.onClick
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv

class ButtonPresenter : Presenter<String>(R.layout.item_button) {

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<String>, payloads: MutableList<Any>?) = with(viewHolder.itemView) {
        logv { "bindViewHolder ${viewHolder.adapterPosition} $item payload=$payloads" }
        label.text = "${item.model}"
        onClick {
            item.onClick(viewHolder)
        }
    }
}