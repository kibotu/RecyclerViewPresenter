package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import net.kibotu.android.recyclerviewpresenter.app.databinding.LabelPresenterItemBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class LabelPresenter extends Presenter<String, LabelPresenterItemBinding> {

    public LabelPresenter() {
        super(R.layout.label_presenter_item, LabelPresenterItemBinding::bind);
    }

    @Override
    public void bindViewHolder(@NonNull LabelPresenterItemBinding viewBinding, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull PresenterViewModel<String> item, @androidx.annotation.Nullable List<Object> payloads) {
        viewBinding.label.setText(item.getModel());
    }
}