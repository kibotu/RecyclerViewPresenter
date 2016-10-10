package net.kibotu.android.recyclerviewpresenter.app;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.kibotu.android.recyclerviewpresenter.BaseViewHolder;
import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;

import butterknife.BindView;

/**
 * Created by Nyaruhodo on 15.05.2016.
 */

class PhotoPresenter extends Presenter<String, PhotoPresenter.ViewHolder> {

    public PhotoPresenter(@NonNull PresenterAdapter<String> presenterAdapter) {
        super(presenterAdapter);
    }

    @LayoutRes
    @Override
    protected int getLayout() {
        return R.layout.photo_presenter_item; // your layout
    }

    /**
     * Hint: Required method; if java had a way of allocating a
     * generic I could move this method into the adapter. :(
     */
    @NonNull
    @Override
    protected ViewHolder createViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
        return new ViewHolder(layout, parent);
    }

    /**
     * Binding your model against your ViewHolder.
     */
    @Override
    public void bindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String item, int position) {

        Glide.with(viewHolder.itemView.getContext())
                .load(item)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.photo);

        viewHolder.itemView.setOnClickListener(v -> {

            // accessing the click listener from the adapter:
            if (presenterAdapter.getOnItemClickListener() != null)
                presenterAdapter.getOnItemClickListener().onItemClick(item, v, position);
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        @NonNull
        @BindView(R.id.photo)
        ImageView photo;

        ViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
            super(layout, parent); // does butterknife binding
        }
    }
}