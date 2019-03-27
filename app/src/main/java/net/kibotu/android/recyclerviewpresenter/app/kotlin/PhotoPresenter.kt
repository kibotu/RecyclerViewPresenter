package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.GlideApp
import net.kibotu.android.recyclerviewpresenter.app.R

class PhotoPresenter : Presenter<RecyclerViewModel<String>>() {

    override val layout: Int = R.layout.photo_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {
        GlideApp.with(this.context.applicationContext)
            .load(item.model)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(photo)
    }
}