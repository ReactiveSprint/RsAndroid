package io.reactivesprint.rx.functions;

import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/12/16.
 */
public class Func1CharSequenceNotNullAndLength<T extends CharSequence> implements Func1<T, Boolean> {

    private final int minimumLength;

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
