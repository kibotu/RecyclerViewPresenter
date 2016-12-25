package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

class ClassExtensions {

    /**
     * Compares two classes by canonical name.
     *
     * @return <code>True</code> if they're equal.
     */
    public static boolean equals(@NonNull final Class first, @NonNull final Class second) {
        return first.getCanonicalName().equals(second.getCanonicalName());
    }
}
