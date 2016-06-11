package io.reactivesprint.rx;

import rx.Observable;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * A read-only property that allows observation of its changes.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public final class Property<V> implements IProperty<V> {
    private final IProperty<V> property;

    /**
     * Constructs a property as a read-only view of {@code property.}
     */
    public Property(IProperty<V> property) {
        checkNotNull(property, "property");
        this.property = property;
    }

    /**
     * Constructs a property that first takes on {@code initialValue}, then each value
     * sent by {@code observable.}
     */
    public Property(V initialValue, Observable<V> observable) {
        checkNotNull(observable, "observable");
        MutableProperty<V> property = new MutableProperty<>(initialValue);
        property.bind(observable);
        this.property = property;
    }

    @Override
    public V getValue() {
        return property.getValue();
    }

    @Override
    public Observable<V> getObservable() {
        return property.getObservable();
    }

    @Override
    public String toString() {
        return "Property{" +
                "property=" + property +
                '}';
    }
}
