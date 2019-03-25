package net.kibotu.android.recyclerviewpresenter.app

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.RecyclerViewModel

class PhotoPresenterV2 : Presenter<RecyclerViewModel<String>>() {

    override val layout: Int = R.layout.photo_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {
        GlideApp.with(this.context.applicationContext)
            .load(item.model)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(photo)
            .clearOnDetach()
    }
}