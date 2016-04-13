package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 */
public class FuncNBooleanAnd implements FuncN<Boolean> {

    /**
     * Lazy Initialization via inner-class holder
     */
    private static class Holder {
        static final FuncNBooleanAnd INSTANCE = new FuncNBooleanAnd();
    }

    private FuncNBooleanAnd() {

    }

    public static FuncNBooleanAnd getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Boolean call(Object... args) {
        boolean result = args != null;
        if (!result) {
            return false;
        }

        for (Object object : args) {
            result = result && object != null;

            if (object instanceof Boolean) {
                result = result && (Boolean) object;
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instance of Boolean.");
            }
        }
        return result;
    }
}
