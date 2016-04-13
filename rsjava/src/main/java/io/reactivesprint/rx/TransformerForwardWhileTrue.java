package io.reactivesprint.rx;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/7/16.
 * <p/>
 * {@link rx.Observable.Transformer} which takes represents {@code shouldForwardObservable} that
 * represents "active" and "inactive" states.
 * And it subscribes to source {@link Observable}
 * whenever {@code shouldForwardObservable} sends true "active" and unsubscribes
 * when {@code shouldForwardObservable} sends false.
 * <br />
 * The Output {@link Observable} completes only when {@code shouldForwardObservable} completes.
 * And errors when either {@code shouldForwardObservable} errors
 * or if source {@link Observable} errors while {@code shouldForwardObservable} is "active".
 */
final class TransformerForwardWhileTrue<T> implements Observable.Transformer<T, T> {
    private final Observable<Boolean> shouldForwardObservable;
    private Subscription observableSubscription;

    public TransformerForwardWhileTrue(Observable<Boolean> shouldForwardObservable) {
        checkNotNull(shouldForwardObservable, "shouldForwardObservable");
        this.shouldForwardObservable = shouldForwardObservable;
    }

    @Override
    public Observable<T> call(final Observable<T> observable) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                Subscription forwardSubscription = shouldForwardObservable.subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Boolean forward) {
                        if (forward) {
                            observableSubscription = observable.subscribe(new Subscriber<T>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    subscriber.onError(e);
                                }

                                @Override
                                public void onNext(T t) {
                                    subscriber.onNext(t);
                                }
                            });

                            subscriber.add(observableSubscription);
                        } else {
                            if (observableSubscription != null) {
                                observableSubscription.unsubscribe();
                            }
                        }
                    }
                });

                subscriber.add(forwardSubscription);
            }
        });
    }
}
