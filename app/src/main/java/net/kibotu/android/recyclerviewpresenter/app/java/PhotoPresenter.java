package net.kibotu.android.recyclerviewpresenter.app.java;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kibotu.android.recyclerviewpresenter.Presenter;
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel;
import net.kibotu.android.recyclerviewpresenter.app.R;
import net.kibotu.android.recyclerviewpresenter.app.databinding.PhotoPresenterItemBinding;
import net.kibotu.android.recyclerviewpresenter.app.misc.GlideApp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by <a href="https://kibotu.net">Jan Rabe</a>.
 */

public class PhotoPresenter extends Presenter<String, PhotoPresenterItemBinding> {

    public PhotoPresenter() {
        super(R.layout.photo_presenter_item, PhotoPresenterItemBinding::bind);
    }

    @Override
    public void bindViewHolder(@NonNull PhotoPresenterItemBinding viewBinding, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull PresenterViewModel<String> item, @androidx.annotation.Nullable List<Object> payloads) {
        GlideApp.with(viewHolder.itemView.getContext().getApplicationContext())
                .load(item.getModel())
                .transition(withCrossFade())
                .into(viewBinding.photo)
                .waitForLayout()
                .clearOnDetach();
    }

}