package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class LabelPresenter : Presenter<String>() {

    override val layout = R.layout.label_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int) = with(viewHolder.itemView) {
        label.text = item.model
    }
}