package net.kibotu.android.recyclerviewpresenter.app.screens.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exozet.android.core.extensions.onClick
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv

class ButtonPresenter : Presenter<String>() {

    override val layout = R.layout.item_button

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) = with(viewHolder.itemView) {
        logv { "bindViewHolder $position $item payload=$payloads" }
        label.text = "$position. ${item.model}"
        onClick {
            item.onItemClickListener?.invoke(item.model, this, position)
        }
    }
}