package io.reactivesprint;

import java.util.Collection;

public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("No instances.");
    }

    public static <T> T checkNotNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " may not be null.");
        } else {
            return object;
        }
    }

    public static <T, L extends Collection<T>> L checkNotNullOrEmpty(L objects, String name) {
        checkNotNull(objects, name);

        if (objects.isEmpty()) {
            throw new IllegalStateException(name + " may not be empty.");
        }

        return objects;
    }

    public static <T> T[] checkNotNullOrEmpty(T[] objects, String name) {
        checkNotNull(objects, name);

        if (objects.length == 0) {
            throw new IllegalStateException(name + " may not be empty.");
        }

        return objects;
    }
}
