package io.reactivesprint.rx.functions;

import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link Func1} which applies "Not" operator to inputs.
 */
public class Func1BooleanNot implements Func1<Boolean, Boolean> {

    /**
     * Lazy Initialization via inner-class holder
     */
    private static class Holder {
        static final Func1BooleanNot INSTANCE = new Func1BooleanNot();
    }

    private Func1BooleanNot() {

    }

    public static Func1BooleanNot getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Boolean call(Boolean aBoolean) {
        return !aBoolean;
    }
}
