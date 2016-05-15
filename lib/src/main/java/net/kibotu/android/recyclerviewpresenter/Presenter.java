package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public abstract class Presenter<T, VH extends RecyclerView.ViewHolder> {

    @NonNull
    protected final PresenterAdapter<T> presenterAdapter;

    public Presenter(@NonNull final PresenterAdapter<T> presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
    }

    @LayoutRes
    protected abstract int getLayout();

    @NonNull
    protected VH onCreateViewHolder(@NonNull final ViewGroup parent) {
        return createViewHolder(getLayout(), parent);
    }

    @NonNull
    protected abstract VH createViewHolder(@LayoutRes final int layout, @NonNull final ViewGroup parent);

    public T get(final int position) {
        return presenterAdapter.get(position);
    }

    public abstract void bindViewHolder(@NonNull final VH viewHolder, @NonNull final T item, final int position);

    @NonNull
    public String tag() {
        return getClass().getSimpleName();
    }

}