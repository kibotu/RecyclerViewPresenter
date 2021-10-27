package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import net.kibotu.android.recyclerviewpresenter.app.databinding.NumberPresenterItemBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class NumberPresenter extends Presenter<Integer, NumberPresenterItemBinding> {

    public NumberPresenter() {
        super(R.layout.number_presenter_item, NumberPresenterItemBinding::bind);
    }

    @Override
    public void bindViewHolder(@NonNull NumberPresenterItemBinding viewBinding, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull PresenterViewModel<Integer> item, @androidx.annotation.Nullable List<Object> payloads) {
        viewBinding.label.setText(item.getModel().toString());
    }

}