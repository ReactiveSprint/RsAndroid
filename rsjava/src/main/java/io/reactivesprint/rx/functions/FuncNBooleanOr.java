package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link FuncN} which applies OR operator to input objects.
 */
public class FuncNBooleanOr implements FuncN<Boolean> {
    /**
     * Lazy Initialization via inner-class holder
     */
    private static class Holder {
        static final FuncNBooleanOr INSTANCE = new FuncNBooleanOr();
    }

    private FuncNBooleanOr() {

    }

    public static FuncNBooleanOr getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Boolean call(Object... args) {
        if (args == null) {
            return false;
        }

        for (Object object : args) {
            if (object == null) {
                continue;
            }

            if (object instanceof Boolean) {
                if ((Boolean) object) {
                    return true;
                }
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instance of Boolean.");
            }
        }
        return false;
    }
}
