package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

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
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.PhotoPresenterItemBinding
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class PhotoPresenter : Presenter<String, PhotoPresenterItemBinding>(
    layout = R.layout.photo_presenter_item,
    viewBindingAccessor = PhotoPresenterItemBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: PhotoPresenterItemBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<String>,
        payloads: MutableList<Any>?
    ) {
        with(viewBinding) {
            GlideApp.with(viewHolder.itemView.context.applicationContext)
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
                .waitForLayout()
                .clearOnDetach()

            progressBar.show()
        }
    }

}