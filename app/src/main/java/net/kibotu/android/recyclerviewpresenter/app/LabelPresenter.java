package net.kibotu.android.recyclerviewpresenter.app;

import android.view.ViewGroup;
import android.widget.TextView;

import net.kibotu.android.recyclerviewpresenter.v1.BaseViewHolder;
import net.kibotu.android.recyclerviewpresenter.v1.Presenter;
import net.kibotu.android.recyclerviewpresenter.v1.PresenterAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class LabelPresenter extends Presenter<String, LabelPresenter.ViewHolder> {

    public LabelPresenter(@NonNull PresenterAdapter<String> presenterAdapter) {
        super(presenterAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.label_presenter_item;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
        return new ViewHolder(layout, parent);
    }

    @Override
    public void bindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String item, int position) {
        viewHolder.label.setText(item);

        viewHolder.itemView.setOnClickListener(v -> {
            if (presenterAdapter.getOnItemClickListener() != null)
                presenterAdapter.getOnItemClickListener().onItemClick(item, v, position);
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        @NonNull
        @BindView(R.id.label)
        TextView label;

        ViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
            super(layout, parent);
        }
    }
}
