package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jan Rabe on 17/09/15.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private Unbinder unbinder;

    public BaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        onBindViewHolder();
    }

    public BaseViewHolder(@LayoutRes final int layout, @NonNull final ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    public void onBindViewHolder() {
        if (unbinder == null)
            unbinder = ButterKnife.bind(this, itemView);
    }

    @CallSuper
    public void onViewDetachedFromWindow() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @NonNull
    public String tag() {
        return getClass().getSimpleName();
    }
}