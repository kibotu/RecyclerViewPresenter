package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;

/**
 * Created by jan.rabe on 31/05/16.
 */

public interface OnEndlessListener {

    void onReachThreshold(@NonNull final PresenterAdapter adapter);
}
