package net.kibotu.android.recyclerviewpresenter.v1;

import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public abstract class Presenter<T, VH extends RecyclerView.ViewHolder> {

    /**
     * Respective adapter.
     */
    @NonNull
    protected final PresenterAdapter<T> presenterAdapter;

    public Presenter(@NonNull final PresenterAdapter<T> presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
    }

    /**
     * Used for inflating the view holder layout.
     *
     * @return Layout Resource Id.
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * Creates a new ViewHolder and inflates the view. Gets called only when the {@link RecyclerView} creates a new view.
     * Cached views go directly to {@link #bindViewHolder(RecyclerView.ViewHolder, Object, int)}
     *
     * @param parent Parent ViewGroup used to set layout parameter.
     * @return new {@link VH}.
     */
    @CallSuper
    @NonNull
    protected VH onCreateViewHolder(@NonNull final ViewGroup parent) {
        return createViewHolder(getLayout(), parent);
    }

    /**
     * Creates a new ViewHolder and inflates the view.
     *
     * @param layout Passed {@link #getLayout()}.
     * @param parent Parent ViewGroup used to set layout parameter.
     * @return new {@link VH}.
     */
    @NonNull
    protected abstract VH createViewHolder(@LayoutRes final int layout, @NonNull final ViewGroup parent);

    /**
     * Gets {@link T} at adapter position.
     *
     * @param position Position of {@link T} in adapter.
     * @return {@link T}.
     */
    public T get(final int position) {
        return presenterAdapter.get(position);
    }

    /**
     * Binds {@link T} to {@link VH}. Use {@link #get(int)} to retrieve neighbour {@link T}.
     *
     * @param viewHolder Current {@link VH}.
     * @param item       Current {@link T}
     * @param position   Adapter position.
     */
    public abstract void bindViewHolder(@NonNull final VH viewHolder, @NonNull final T item, final int position);

    /**
     * Used for logging purposes.
     */
    @NonNull
    public String tag() {
        return getClass().getSimpleName();
    }

}