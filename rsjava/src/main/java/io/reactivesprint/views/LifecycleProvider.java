package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import rx.Observable;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Provides when to start and stop binding of {@link IViewModel} to views.
 */
public abstract class LifecycleProvider<E> {
    public static <E> LifecycleProvider<E> from(final Observable<E> lifecycle, final E startEvent, final E stopEvent) {
        checkNotNull(lifecycle, "lifecycle");
        checkNotNull(startEvent, "startEvent");
        checkNotNull(stopEvent, "stopEvent");
        return new LifecycleProvider<E>() {
            @Override
            public Observable<E> onStartBinding() {
                return lifecycle.filter(new Func1<E, Boolean>() {
                    @Override
                    public Boolean call(E e) {
                        return startEvent.equals(e);
                    }
                });
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                checkNotNull(lifecycle, "lifecycle");
                checkNotNull(stopEvent, "stopEvent");
                return new Observable.Transformer<T, T>() {
                    @Override
                    public Observable<T> call(Observable<T> source) {
                        return source.takeUntil(
                                lifecycle.takeFirst(new Func1<E, Boolean>() {
                                    @Override
                                    public Boolean call(E event) {
                                        return event.equals(stopEvent);
                                    }
                                })
                        );
                    }
                };
            }
        };
    }

    /**
     * @return A sequence which starts binding.
     */
    public abstract Observable<E> onStartBinding();

    /**
     * Binds a source until the next reasonable event occurs.
     * <p/>
     * Intended for use with {@link Observable#compose(Observable.Transformer)}
     *
     * @return a reusable {@link Observable.Transformer} which unsubscribes at the correct time.
     */
    public abstract <T> Observable.Transformer<T, T> bindToLifecycle();
}
