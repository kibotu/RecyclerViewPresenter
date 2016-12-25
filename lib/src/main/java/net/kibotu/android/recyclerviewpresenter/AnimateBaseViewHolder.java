package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class AnimateBaseViewHolder extends AnimateViewHolder implements IBaseViewHolder {

    protected Unbinder unbinder;

    /**
     * Creates a new ViewHolder and calls {@link #onBindViewHolder()} in order to bind the view using {@link ButterKnife}.
     *
     * @param itemView
     */
    public AnimateBaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        onBindViewHolder();
    }

    /**
     * Inflates the layout and binds it to the ViewHolder using {@link ButterKnife}.
     */
    public AnimateBaseViewHolder(@LayoutRes final int layout, @NonNull final ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    /**
     * Binds all views to the ViewHolder using ButterKnife.
     */
    @Override
    public void onBindViewHolder() {
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
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void animateAddImpl(ViewPropertyAnimatorListener listener) {

    }

    @Override
    public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {

    }
}
