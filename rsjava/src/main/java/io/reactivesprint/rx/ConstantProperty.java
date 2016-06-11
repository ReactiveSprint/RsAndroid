package io.reactivesprint.rx;

import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * A property that never changes.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public final class ConstantProperty<V> implements IProperty<V> {
    private final V value;

    /**
     * Constructs a property with {@code value}
     */
    public ConstantProperty(V value) {
        this.value = value;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public Observable<V> getObservable() {
        return Observable.just(value);
    }

    @Override
    public String toString() {
        return "ConstantProperty{" +
                "value=" + value +
                '}';
    }
}
