package io.reactivesprint.internal;

public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("No instances.");
    }

    public static <T> T checkNotNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + "may not be null.");
        } else {
            return object;
        }
    }
}
