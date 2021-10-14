package net.kibotu.android.recyclerviewpresenter.app.screens.kotlin

import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.databinding.NumberPresenterItemBinding

class NumberPresenter : Presenter<Int, NumberPresenterItemBinding>(
    layout = R.layout.number_presenter_item,
    viewBindingAccessor = NumberPresenterItemBinding::bind
) {

    override fun bindViewHolder(
        viewBinding: NumberPresenterItemBinding,
        viewHolder: RecyclerView.ViewHolder,
        item: PresenterViewModel<Int>,
        payloads: MutableList<Any>?
    ) {
        viewBinding.label.text = "${item.model}"
    }

}