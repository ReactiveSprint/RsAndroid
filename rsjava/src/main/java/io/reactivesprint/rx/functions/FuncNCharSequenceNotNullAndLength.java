package io.reactivesprint.rx.functions;

import rx.functions.FuncN;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link FuncN} which returns true when all input objects are not null
 * and {@link CharSequence#length()} {@code >= minimumLength}
 * <p/>
 * All input objects must be instance of {@link CharSequence}.
 */
public class FuncNCharSequenceNotNullAndLength implements FuncN<Boolean> {

    private final int minimumLength;

    /**
     * Creates instance with {@code minimumLength = 1}
     */
    public FuncNCharSequenceNotNullAndLength() {
        this(1);
    }

    public FuncNCharSequenceNotNullAndLength(int minimumLength) {
        this.minimumLength = minimumLength;
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
                if (((CharSequence) object).length() < minimumLength) {
                    return false;
                }
            } else {
                throw new AssertionError(getClass().getName() + " expects objects to be instance of CharSequence.");
            }
        }
        return true;
    }
}
