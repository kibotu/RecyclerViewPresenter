package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.logv

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class LabelPresenter : Presenter<String>() {

    override val layout = R.layout.label_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) = with(viewHolder.itemView) {
        logv("bindViewHolder $position $item payload=$payloads")
        label.text = "$position. ${item.model}"
    }
}