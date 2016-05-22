package io.reactivesprint;

import java.util.Collection;

public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("No instances.");
    }

    public static <T> T checkNotNull(T object, String name) {
        return checkNotNullWithMessage(object, name + " may not be null.");
    }

    public static <T> T checkNotNullWithMessage(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }

        return object;
    }

    public static <T, L extends Collection<T>> L checkNotNullOrEmpty(L objects, String name) {
        return checkNotNullOrEmptyWithMessage(objects, name + " may not be empty.");
    }

    public static <T, L extends Collection<T>> L checkNotNullOrEmptyWithMessage(L objects, String message) {
        checkNotNullWithMessage(objects, message);

        if (objects.isEmpty()) {
            throw new IllegalStateException(message);
        }

        return objects;
    }

    public static <T> T[] checkNotNullOrEmpty(T[] objects, String name) {
        return checkNotNullOrEmptyWithMessage(objects, name + " may not be empty.");
    }

    public static <T> T[] checkNotNullOrEmptyWithMessage(T[] objects, String message) {
        checkNotNullWithMessage(objects, message);

        if (objects.length == 0) {
            throw new IllegalStateException(message);
        }

        return objects;
    }

    public static <T> T checkNotNullAndInstanceOf(T object, Class<?> aClass, String name) {
        return checkNotNullAndInstanceOfWithMessage(object, aClass, name + " must be instance of " + aClass.getName());
    }

    public static <T> T checkNotNullAndInstanceOfWithMessage(T object, Class<?> aClass, String message) {
        checkNotNull(object, message);

        if (!object.getClass().isAssignableFrom(aClass)) {
            throw new IllegalArgumentException(message);
        }

        return object;
    }

}
