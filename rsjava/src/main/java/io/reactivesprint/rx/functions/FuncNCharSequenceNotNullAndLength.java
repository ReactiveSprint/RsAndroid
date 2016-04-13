package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 */
public class FuncNCharSequenceNotNullAndLength implements FuncN<Boolean> {

    private final int minimumLength;

    public FuncNCharSequenceNotNullAndLength() {
        this(0);
    }

    public FuncNCharSequenceNotNullAndLength(int minimumLength) {
        this.minimumLength = minimumLength;
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
                notNull = notNull && ((CharSequence) object).length() >= minimumLength;
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instanceof CharSequence.");
            }
        }
        return notNull;
    }
}
