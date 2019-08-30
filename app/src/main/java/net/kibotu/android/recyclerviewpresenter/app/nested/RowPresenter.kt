package net.kibotu.android.recyclerviewpresenter.app.nested

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.item_icon_with_label.view.*
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R

data class Row(val image: String, val label: String, val subTitle: String? = null)

class RowPresenter : Presenter<Row>() {

    override val layout = R.layout.item_icon_with_label

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<Row>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) {

        with(viewHolder.itemView) {

            Glide.with(this)
                .asBitmap()
                .load(item.model.image)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageCard)
                .waitForLayout()
                .clearOnDetach()
        }
    }
}