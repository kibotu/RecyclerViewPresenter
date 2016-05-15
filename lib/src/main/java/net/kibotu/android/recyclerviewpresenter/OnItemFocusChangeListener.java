package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;
import android.view.View;

public interface OnItemFocusChangeListener<T> {
    void onFocusChange(@NonNull final T item, @NonNull final View view, boolean hasFocus);
}