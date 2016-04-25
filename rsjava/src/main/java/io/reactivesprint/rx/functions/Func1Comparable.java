package io.reactivesprint.rx.functions;

import java.util.Comparator;

/**
 * Created by Ahmad Baraka on 4/17/16.
 */
public class Func1Comparable<T extends Comparable<T>> extends Func1Comparator<T> {
    public Func1Comparable(int compareResult, T object) {
        super(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        }, compareResult, object);
    }
}
