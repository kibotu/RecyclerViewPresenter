package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class LabelPresenter : Presenter<String>() {

    override val layout = R.layout.label_presenter_item

    private val View.label: TextView
        get() = findViewById(R.id.label)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) = with(viewHolder.itemView) {
        logv { "bindViewHolder $position $item payload=$payloads" }
        label.text = "$position. ${item.model}"
    }
}