package net.kibotu.android.recyclerviewpresenter.app.screens.nested

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.ItemIconWithLabelBinding

data class Row(val image: String, val label: String, val subTitle: String? = null)

class RowPresenter : Presenter<Row, ItemIconWithLabelBinding>(
    layout = R.layout.item_icon_with_label,
    viewBindingAccessor = ItemIconWithLabelBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: ItemIconWithLabelBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<Row>,
        payloads: MutableList<Any>?
    ) {
        with(viewBinding) {
            Glide.with(root)
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