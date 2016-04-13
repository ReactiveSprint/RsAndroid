package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link FuncN} which returns true if all input objects are not null.
 */
public class FuncNNotNull implements FuncN<Boolean> {

    /**
     * Lazy Initialization via inner-class holder
     */
    private static class Holder {
        static final FuncNNotNull INSTANCE = new FuncNNotNull();
    }

    private FuncNNotNull() {

    }

    public static FuncNNotNull getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Boolean call(Object... args) {
        if (args == null) {
            return false;
        }

        for (Object object : args) {
            if (object == null) {
                return false;
            }
        }
        return true;
    }
}
