package io.reactivesprint.rx;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static io.reactivesprint.internal.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/7/16.
 * <p/>
 * {@link rx.Observable.Transformer} which takes {@code throttleWhileFalseObservable} that
 * represents "active" and "inactive" states.
 * And throttles source {@link Observable} whenever {@code throttleWhileFalseObservable} sends false as "inactive"
 * by applying {@link Observable#compose(Observable.Transformer)} {@code throttleTransformer}
 * or applying {@link Observable#lift(Observable.Operator)} {@code throttleOperator}.
 * <br />
 * The output {@link Observable} subscribes to source and completes when either source
 * or {@code throttleWhileFalseObservable} complete. And errors when either errors as well.
 *
 * @see TransformerForwardWhileTrue
 */
final class TransformerThrottleWhileFalse<T> implements Observable.Transformer<T, T> {
    private final Observable<Boolean> throttleWhileFalseObservable;
    private final Observable.Transformer<T, T> throttleTransformer;
    private final Observable.Operator<T, T> throttleOperator;

    public TransformerThrottleWhileFalse(Observable<Boolean> throttleWhileFalseObservable, Observable.Transformer<T, T> throttleTransformer) {
        checkNotNull(throttleWhileFalseObservable, "throttleWhileFalseObservable");
        checkNotNull(throttleTransformer, "throttleTransformer");
        this.throttleWhileFalseObservable = throttleWhileFalseObservable;
        this.throttleTransformer = throttleTransformer;
        this.throttleOperator = null;
    }

    public TransformerThrottleWhileFalse(Observable<Boolean> throttleWhileFalseObservable, Observable.Operator<T, T> throttleOperator) {
        checkNotNull(throttleWhileFalseObservable, "throttleWhileFalseObservable");
        checkNotNull(throttleOperator, "throttleOperator");
        this.throttleWhileFalseObservable = throttleWhileFalseObservable;
        this.throttleTransformer = null;
        this.throttleOperator = throttleOperator;
    }

    @Override
    public Observable<T> call(final Observable<T> observable) {
        return Observable.switchOnNext(Observable.create(new Observable.OnSubscribe<Observable<T>>() {
            @Override
            public void call(final Subscriber<? super Observable<T>> subscriber) {
                Subscription subscription = throttleWhileFalseObservable.subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            subscriber.onNext(observable);
                        } else {
                            if (throttleTransformer != null) {
                                subscriber.onNext(observable.compose(throttleTransformer));
                            } else {
                                subscriber.onNext(observable.lift(throttleOperator));
                            }
                        }
                    }
                });

                subscriber.add(subscription);
            }
        }).takeUntil(observable.ignoreElements()))
                .takeUntil(throttleWhileFalseObservable.ignoreElements());
    }
}
