package io.reactivesprint.rx.functions;

import java.util.Comparator;

import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/16/16.
 */
public class Func1Comparator<T> implements Func1<T, Boolean> {

    private final Comparator<T> comparator;
    private final T object;
    private final int compareResult;

    public Func1Comparator(Comparator<T> comparator, T object, int compareResult) {
        checkNotNull(comparator, "comparator");
        this.comparator = comparator;
        this.object = object;
        this.compareResult = compareResult;
    }

    @Override
    public final Boolean call(T t) {
        return comparator.compare(object, t) == compareResult;
    }
}
