package io.reactivesprint.rx;

import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents a property that allows observation of its changes.
 * <p/>
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public interface IProperty<V> {
    /**
     * @return Current value of the property.
     */
    V getValue();

    /**
     * @return An Observable that once subscribed, sends the current value
     * then will send property's changes over time.
     */
    Observable<V> getObservable();
}
