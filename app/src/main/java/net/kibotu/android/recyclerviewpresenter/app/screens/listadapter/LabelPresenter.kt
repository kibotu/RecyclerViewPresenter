package net.kibotu.android.recyclerviewpresenter.app.screens.listadapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exozet.android.core.extensions.onClick
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.PresenterViewModel
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class LabelPresenter : Presenter<String>(R.layout.label_presenter_item) {

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<String>, payloads: MutableList<Any>?) = with(viewHolder.itemView) {
        logv { "bindViewHolder ${viewHolder.adapterPosition} $item payload=$payloads" }
        label.text = "${viewHolder.adapterPosition}. ${item.model}"

        onClick {
            item.onClick(viewHolder)
        }
    }
}