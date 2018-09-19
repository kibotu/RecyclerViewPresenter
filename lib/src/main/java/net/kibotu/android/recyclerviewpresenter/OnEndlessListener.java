package net.kibotu.android.recyclerviewpresenter;

import androidx.annotation.NonNull;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public interface OnEndlessListener {

    void onReachThreshold(@NonNull final PresenterAdapter adapter);
}
