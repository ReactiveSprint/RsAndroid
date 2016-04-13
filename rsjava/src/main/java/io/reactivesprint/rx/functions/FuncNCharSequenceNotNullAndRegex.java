package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class FuncNCharSequenceNotNullAndRegex implements FuncN<Boolean> {
    private final String regex;

    public FuncNCharSequenceNotNullAndRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public Boolean call(Object... args) {
        boolean notNull = args != null;
        if (!notNull) {
            return false;
        }

        for (Object object : args) {
            notNull = notNull && object != null;
            if (object instanceof CharSequence) {
                notNull = notNull && object.toString().matches(regex);
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instanceof CharSequence.");
            }
        }
        return notNull;
    }
}
