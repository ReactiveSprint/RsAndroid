package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
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
        boolean notNull = args != null;
        if (!notNull) {
            return false;
        }

        for (Object object : args) {
            notNull = notNull && object != null;
        }
        return notNull;
    }
}
