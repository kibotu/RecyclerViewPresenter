package net.kibotu.android.recyclerviewpresenter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import net.kibotu.android.recyclerviewpresenter.v2.IBaseViewHolder;
import net.kibotu.android.recyclerviewpresenter.v2.UIDGenerator;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements IBaseViewHolder {

    private static final String TAG = BaseViewHolder.class.getSimpleName();

    public String uuid = String.valueOf(UIDGenerator.INSTANCE.newUID());

    protected Unbinder unbinder;

    public boolean debug = false;

    /**
     * Creates a new ViewHolder and calls {@link #onBindViewHolder()} in order to bind the view using {@link ButterKnife}.
     *
     * @param itemView
     */
    public BaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        if (debug)
            Log.v(TAG, "[create] " + uuid);
        onBindViewHolder();
    }

    /**
     * Inflates the layout and binds it to the ViewHolder using {@link ButterKnife}.
     */
    public BaseViewHolder(@LayoutRes final int layout, @NonNull final ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    /**
     * Binds all views to the ViewHolder using ButterKnife.
     */
    @Override
    public void onBindViewHolder() {
        if (debug)
            Log.v(TAG, "[onBindViewHolder] " + uuid);
        if (unbinder == null)
            unbinder = ButterKnife.bind(this, itemView);
    }

    /**
     * Is called when it gets detached from the view.
     * It also removes all listeners like click, focusChange listeners and background selectors.
     */
    @CallSuper
    @Override
    public void onViewDetachedFromWindow() {
        if (debug)
            Log.v(TAG, "[onViewDetachedFromWindow] " + uuid);
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}