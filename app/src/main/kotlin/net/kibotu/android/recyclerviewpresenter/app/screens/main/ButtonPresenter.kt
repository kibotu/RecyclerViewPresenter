package net.kibotu.android.recyclerviewpresenter.app.screens.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.ItemButtonBinding
import net.kibotu.logger.Logger.logv

class ButtonPresenter : Presenter<String, ItemButtonBinding>(
    layout = R.layout.item_button,
    viewBindingAccessor = ItemButtonBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: ItemButtonBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<String>,
        payloads: MutableList<Any>?
    ) = with(viewBinding) {
        logv { "bindViewHolder ${viewHolder.adapterPosition} $item payload=$payloads" }
        label.text = "${item.model}"
        root.setOnClickListener {
            item.onClick(viewHolder)
        }
    }

}