package net.kibotu.android.recyclerviewpresenter.app.kotlin

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class PhotoPresenter : Presenter<String>() {

    override val layout: Int = R.layout.photo_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter): Unit = with(viewHolder.itemView) {
        GlideApp.with(this.context.applicationContext)
            .load(item.model)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(photo)
    }
}