package io.reactivesprint.rx.functions;

import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link Func1} which returns true if input is not null.
 *
 * @see Func1CharSequenceNotNullAndLength
 */
public class Func1NotNull<T> implements Func1<T, Boolean> {

    /**
     * Lazy Initialization via inner-class holder
     */
    private static class Holder {
        static final Func1NotNull INSTANCE = new Func1NotNull();
    }

    private Func1NotNull() {

    }

    @SuppressWarnings("unchecked")
    public static <T> Func1NotNull<T> getInstance() {
        return (Func1NotNull<T>) Holder.INSTANCE;
    }

    @Override
    public Boolean call(T t) {
        return t != null;
    }
}
