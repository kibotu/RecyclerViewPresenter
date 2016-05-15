package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;
import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(@NonNull final T item, @NonNull final View rowView, final int position);
}