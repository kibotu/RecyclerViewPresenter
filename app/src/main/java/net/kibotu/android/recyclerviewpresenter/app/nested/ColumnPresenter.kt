package net.kibotu.android.recyclerviewpresenter.app.nested

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import kotlinx.android.synthetic.main.item_icon_with_label.view.*
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.RecyclerViewHolder
import net.kibotu.android.recyclerviewpresenter.app.R

data class Column(val image: String)

class ColumnPresenter : Presenter<Column>() {

    override val layout = R.layout.item_column

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<Column>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) {

        with(viewHolder.itemView) {
            Glide.with(this)
                .asBitmap()
                .load(item.model.image)
                .transition(BitmapTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageCard)
                .waitForLayout()
                .clearOnDetach()
        }
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