package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel;
import net.kibotu.android.recyclerviewpresenter.app.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class LabelPresenter extends Presenter<String> {

    public LabelPresenter() {
        super(R.layout.label_presenter_item);
    }

    @Override
    public void bindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, @NotNull PresenterViewModel<String> item, @Nullable List<Object> payloads) {
        TextView label = viewHolder.itemView.findViewById(R.id.label);
        label.setText(item.getModel());
    }
}