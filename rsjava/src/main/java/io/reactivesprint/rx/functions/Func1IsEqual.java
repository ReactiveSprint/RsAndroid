package io.reactivesprint.rx.functions;

import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/16/16.
 * <p/>
 * {@link Func1} which returns true if input is equal to {@code object}.
 */
public final class Func1IsEqual<T> implements Func1<T, Boolean> {

    private final T object;

    public Func1IsEqual(T object) {
        checkNotNull(object, "object");
        this.object = object;
    }

    @Override
    public Boolean call(T t) {
        return object.equals(t);
    }
}
