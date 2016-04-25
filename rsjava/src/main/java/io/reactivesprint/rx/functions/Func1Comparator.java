package io.reactivesprint.rx.functions;

import java.util.Comparator;

import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/16/16.
 * <p/>
 * {@link Func1} which returns true if input is {@code compareResult} than {@code object}
 * <pre>
 *     // This instance sends true if sent values are GREATER than 0.
 *     new Func1Comparator(comparator, GREATER_THAN, 0);
 * </pre>
 */
public class Func1Comparator<T> implements Func1<T, Boolean> {
    public static final int GREATER_THAN_OR_EQUAL = 2;
    public static final int GREATER_THAN = 1;
    public static final int EQUAL = 0;
    public static final int LESS_THAN = -1;
    public static final int LESS_THAN_OR_EQUAL = -2;

    private final Comparator<T> comparator;
    private final T object;
    private final int compareResult;

    /**
     * Creates an instances which returns true if input is {@code compareResult} than {@code object}
     *
     * @param comparator    Comparator used to compare {@code object} with any sent inputs.
     * @param compareResult Can be any of {@link #GREATER_THAN_OR_EQUAL}, {@link #GREATER_THAN},
     *                      {@link #EQUAL}, {@link #LESS_THAN} or {@link #LESS_THAN_OR_EQUAL}
     * @param object        An instance used to compare sent values.
     */
    public Func1Comparator(Comparator<T> comparator, int compareResult, T object) {
        checkNotNull(comparator, "comparator");
        if (compareResult > GREATER_THAN_OR_EQUAL || compareResult < LESS_THAN_OR_EQUAL) {
            throw new IllegalStateException("Invalid compareResult input. " +
                    "Must be any of GREATER_THAN_OR_EQUAL, GREATER_THAN" +
                    ", EQUAL" +
                    "LESS_THAN, LESS_THAN_OR_EQUAL");
        }
        this.comparator = comparator;
        this.object = object;
        this.compareResult = compareResult;
    }

    @Override
    public final Boolean call(T t) {
        int result = comparator.compare(t, object);

        if (result == 0) {
            return compareResult == EQUAL
                    || compareResult == LESS_THAN_OR_EQUAL
                    || compareResult == GREATER_THAN_OR_EQUAL;
        } else if (result >= 1) {
            return compareResult == GREATER_THAN
                    || compareResult == GREATER_THAN_OR_EQUAL;
        } else {
            return compareResult == LESS_THAN
                    || compareResult == LESS_THAN_OR_EQUAL;
        }
    }
}
