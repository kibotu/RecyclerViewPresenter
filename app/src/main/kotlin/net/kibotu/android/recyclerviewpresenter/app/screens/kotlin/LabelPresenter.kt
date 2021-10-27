package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.LabelPresenterItemBinding
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class LabelPresenter : Presenter<String, LabelPresenterItemBinding>(
    layout = R.layout.label_presenter_item,
    viewBindingAccessor = LabelPresenterItemBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: LabelPresenterItemBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<String>,
        payloads: MutableList<Any>?
    ) = with(viewBinding) {
        logv { "bindViewHolder ${viewHolder.adapterPosition} $item payload=$payloads" }
        label.text = "${item.model}"
    }

}