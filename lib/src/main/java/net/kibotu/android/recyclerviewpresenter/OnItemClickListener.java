package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;
import android.view.View;
/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public interface OnItemClickListener<T> {

    /**
     * Invokes click listener.
     *
     * @param item     Model of the adapter.
     * @param rowView  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    void onItemClick(@NonNull final T item, @NonNull final View rowView, final int position);
}