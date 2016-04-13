package io.reactivesprint.rx.functions;

import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/12/16.
 * <p/>
 * {@link Func1} which returns true if input is not null
 * and {@link CharSequence#length()} {@code >= minimumLength}
 *
 * @see Func1NotNull
 * @see Func1CharSequenceNotNullAndRegex
 */
public class Func1CharSequenceNotNullAndLength<T extends CharSequence> implements Func1<T, Boolean> {

    private final int minimumLength;

    /**
     * Creates instance with {@code minimumLength = 1}
     */
    public Func1CharSequenceNotNullAndLength() {
        this(1);
    }

    public Func1CharSequenceNotNullAndLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Override
    public Boolean call(T t) {
        return t != null
                && t.length() >= minimumLength;
    }
}
