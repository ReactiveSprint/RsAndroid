package io.reactivesprint.views;

import rx.Observable;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/22/16.
 * <p/>
 * Utility class for creating {@link ILifecycleProvider}
 */
public final class LifecycleProviders {
    private LifecycleProviders() {
    }

    public static <E> ILifecycleProvider<E> from(final Observable<E> lifecycle, final E startEvent, final E stopEvent) {
        checkNotNull(lifecycle, "lifecycle");
        checkNotNull(startEvent, "startEvent");
        checkNotNull(stopEvent, "stopEvent");
        return new ILifecycleProvider<E>() {
            @Override
            public Observable<E> onStartBinding() {
                return lifecycle
                        .distinctUntilChanged()
                        .filter(new Func1<E, Boolean>() {
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
}
