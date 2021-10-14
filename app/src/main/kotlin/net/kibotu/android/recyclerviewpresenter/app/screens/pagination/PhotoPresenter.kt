package net.kibotu.android.recyclerviewpresenter.app.screens.pagination

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.PhotoPresenterItemBinding
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp

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
            val uri = Uri.parse(item.model)
            val width = uri.pathSegments.takeLast(2).first().toInt()
            val height = uri.pathSegments.last().toInt()

            photo.minimumWidth = width
            photo.minimumHeight = height

            GlideApp.with(root.context.applicationContext)
                .load(uri)
                .transition(withCrossFade())
                .into(photo)
                .clearOnDetach()
        }
    }

}