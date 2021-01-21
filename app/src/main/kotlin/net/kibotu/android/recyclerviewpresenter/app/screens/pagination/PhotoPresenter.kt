package net.kibotu.android.recyclerviewpresenter.app.screens.pagination

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp

class PhotoPresenter : Presenter<String>(R.layout.photo_presenter_item) {

    private val View.photo: ImageView
        get() = findViewById(R.id.photo)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<String>, payloads: MutableList<Any>?): Unit = with(viewHolder.itemView) {

        val uri = Uri.parse(item.model)
        val width = uri.pathSegments.takeLast(2).first().toInt()
        val height = uri.pathSegments.last().toInt()

        photo.minimumWidth = width
        photo.minimumHeight = height

        GlideApp.with(this.context.applicationContext)
            .load(uri)
            .transition(withCrossFade())
            .into(photo)
            .clearOnDetach()
    }
}