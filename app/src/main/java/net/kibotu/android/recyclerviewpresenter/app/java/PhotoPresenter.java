package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import net.kibotu.android.recyclerviewpresenter.Adapter;
import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class PhotoPresenter extends Presenter<String> {

    @Override
    public int getLayout() {
        return R.layout.photo_presenter_item;
    }

    @Override
    public void bindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, @NotNull PresenterModel<String> item, int position, @Nullable List<Object> payloads, @NotNull Adapter adapter) {
        ImageView photo = viewHolder.itemView.findViewById(R.id.photo);

        GlideApp.with(viewHolder.itemView.getContext().getApplicationContext())
                .load(item.getModel())
                .transition(withCrossFade())
                .into(photo)
                .waitForLayout()
                .clearOnDetach();
    }
}