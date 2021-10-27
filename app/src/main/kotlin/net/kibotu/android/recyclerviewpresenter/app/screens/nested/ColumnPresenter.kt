package net.kibotu.android.recyclerviewpresenter.app.screens.nested

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.RecyclerViewHolder
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.ItemColumnBinding

data class Column(val image: String)

class ColumnPresenter : Presenter<Column, ItemColumnBinding>(
    layout = R.layout.item_column,
    viewBindingAccessor = ItemColumnBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: ItemColumnBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<Column>,
        payloads: MutableList<Any>?
    ) {
        Glide.with(viewHolder.itemView)
            .asBitmap()
            .load(item.model.image)
            .transition(BitmapTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewBinding.imageCard)
            .waitForLayout()
            .clearOnDetach()
    }

    override fun onCreateViewHolder(parent: ViewGroup) = object : RecyclerViewHolder(parent, layout) {

        override fun onViewAttachedToWindow() {
            super.onViewAttachedToWindow()
            Log.v("Column-VH", "onViewAttachedToWindow $adapterPosition $uuid")
        }

        override fun onViewDetachedFromWindow() {
            super.onViewDetachedFromWindow()
            Log.v("Column-VH", "onViewDetachedFromWindow $adapterPosition $uuid")
        }

        override fun onViewRecycled() {
            Log.v("Column-VH", "onViewRecycled $adapterPosition $uuid")
            super.onViewRecycled()
        }

        override fun onFailedToRecycleView(): Boolean {
            Log.v("Column-VH", "onFailedToRecycleView $adapterPosition $uuid")
            return super.onFailedToRecycleView()
        }
    }
}