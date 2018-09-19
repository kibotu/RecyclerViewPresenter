package net.kibotu.android.recyclerviewpresenter;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public interface OnItemFocusChangeListener<T> {

    /**
     * Invokes click listener.
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus <code>True</code> if it is focused.
     */
    void onFocusChange(@NonNull final T item, @NonNull final View view, boolean hasFocus);
}