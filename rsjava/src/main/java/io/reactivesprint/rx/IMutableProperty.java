package io.reactivesprint.rx;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * Represents an observable property that can be mutated directly.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public interface IMutableProperty<V> extends IProperty<V> {
    /**
     * Sets value of the receiver.
     */
    void setValue(V value);

    /**
     * Binds an {@link Observable} to the receiver, updating the receiver's value to the latest
     * value sent by the {@code observable.}
     * <p/>
     * The binding will automatically terminate when the receiver is finalized,
     * or when the {@code observable} completes.
     *
     * @see MutableProperty#bind(IMutableProperty, Observable)
     */
    Subscription bind(Observable<V> source);

    /**
     * Binds to the latest values of {@code source} to the receiver.
     * <p/>
     * The binding will automatically terminate when either the receiver or {@code source} is finalized.
     *
     * @see MutableProperty#bind(IMutableProperty, IProperty)
     */
    Subscription bind(IProperty<V> source);
}
