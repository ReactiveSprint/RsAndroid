package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Provides when to start and stop binding of {@link IViewModel} to views.
 */
public interface ILifecycleProvider<E> {
    /**
     * @return A sequence which starts binding.
     */
    Observable<E> onStartBinding();

    /**
     * Binds a source until the next reasonable event occurs.
     * <p/>
     * Intended for use with {@link Observable#compose(Observable.Transformer)}
     *
     * @return a reusable {@link Observable.Transformer} which unsubscribes at the correct time.
     */
    <T> Observable.Transformer<T, T> bindToLifecycle();
}
