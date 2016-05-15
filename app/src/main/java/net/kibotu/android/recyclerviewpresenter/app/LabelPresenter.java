package net.kibotu.android.recyclerviewpresenter.app;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kibotu.android.recyclerviewpresenter.BaseViewHolder;
import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;

import butterknife.BindView;

/**
 * Created by Nyaruhodo on 15.05.2016.
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

        public ViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
            super(layout, parent);
        }
    }
}
