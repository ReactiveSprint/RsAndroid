package io.reactivesprint.android;

import android.os.Looper;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("No instances.");
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new AssertionError(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
    }
}
