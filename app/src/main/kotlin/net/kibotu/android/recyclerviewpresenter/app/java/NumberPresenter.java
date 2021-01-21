package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import net.kibotu.android.recyclerviewpresenter.Adapter;
import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class NumberPresenter extends Presenter<String> {

    @Override
    public int getLayout() {
        return R.layout.number_presenter_item;
    }

    @Override
    public void bindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, @NotNull PresenterModel<String> item, int position, @Nullable List<Object> payloads, @NotNull Adapter adapter) {
        TextView label = viewHolder.itemView.findViewById(R.id.label);
        label.setText(item.getModel());
    }
}