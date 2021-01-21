package net.kibotu.android.recyclerviewpresenter.app.screens.listadapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp
import net.kibotu.android.recyclerviewpresenter.v2.Presenter
import net.kibotu.android.recyclerviewpresenter.v2.PresenterViewModel

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class PhotoPresenter : Presenter<String>(R.layout.photo_presenter_item) {

    private val View.progressBar: ContentLoadingProgressBar
        get() = findViewById(R.id.progressBar)

    private val View.photo: ImageView
        get() = findViewById(R.id.photo)

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterViewModel<String>, payloads: MutableList<Any>?): Unit = with(viewHolder.itemView) {

        GlideApp.with(context.applicationContext)
            .load(item.model)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.hide()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.hide()
                    return false
                }
            })
            .into(photo)

        progressBar.show()
    }
}