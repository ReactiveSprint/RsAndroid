package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link FuncN} which applies AND operator to input objects.
 * <p/>
 * {@code null} inputs are considered {@code false}
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
        if (args == null) {
            return false;
        }

        for (Object object : args) {
            if (object == null) {
                return false;
            }

            if (object instanceof Boolean) {
                if (!(Boolean) object) {
                    return false;
                }
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instance of Boolean.");
            }
        }
        return true;
    }
}
