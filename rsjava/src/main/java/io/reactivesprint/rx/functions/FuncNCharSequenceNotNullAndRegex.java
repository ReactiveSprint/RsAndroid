package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/13/16.
 * <p/>
 * {@link FuncN} which returns true when all input objects are not null
 * and {@link String#matches(String)} {@code regex}
 * <p/>
 * All input objects must be instance of {@link CharSequence}.
 */
public class FuncNCharSequenceNotNullAndRegex implements FuncN<Boolean> {
    private final String regex;

    public FuncNCharSequenceNotNullAndRegex(String regex) {
        this.regex = regex;
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

            if (object instanceof CharSequence) {
                if (!object.toString().matches(regex)) {
                    return false;
                }
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instance of CharSequence.");
            }
        }
        return true;
    }
}
