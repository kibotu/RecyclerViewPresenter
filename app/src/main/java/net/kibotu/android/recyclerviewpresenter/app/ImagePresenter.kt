package net.kibotu.android.recyclerviewpresenter.app

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.RecyclerViewModel

class ImagePresenter : Presenter<RecyclerViewModel<String>>() {

    override val layout: Int = R.layout.photo_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {
        GlideApp.with(context)
            .load(item.model)
            .into(photo)
    }
}