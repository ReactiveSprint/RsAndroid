package io.reactivesprint.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * A read-only property that allows observation of its changes.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public class Property<Value> implements PropertyType<Value> {
    private final PropertyType<Value> property;

    /**
     * Constructs a property as a read-only view of {@code property.}
     */
    public Property(@NonNull PropertyType<Value> property) {
        this.property = property;
    }

    /**
     * Constructs a property that first takes on {@code initialValue}, then each value
     * sent by {@code observable.}
     */
    public Property(@Nullable Value initialValue, @NonNull Observable<Value> observable) {
        MutableProperty<Value> property = new MutableProperty<>(initialValue);
        property.bind(observable);
        this.property = property;
    }

    @Override
    public Value getValue() {
        return property.getValue();
    }

    @Override
    public Observable<Value> getObservable() {
        return property.getObservable();
    }
}
