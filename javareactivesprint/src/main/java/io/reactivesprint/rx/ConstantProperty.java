package io.reactivesprint.rx;

import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * A property that never changes.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public final class ConstantProperty<Value> implements PropertyType<Value> {
    private final Value value;

    /**
     * Constructs a property with {@code value}
     */
    public ConstantProperty(Value value) {
        this.value = value;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public Observable<Value> getObservable() {
        return Observable.just(value);
    }
}
