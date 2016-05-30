package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;

/**
 * Created by Nyaruhodo on 15.05.2016.
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
