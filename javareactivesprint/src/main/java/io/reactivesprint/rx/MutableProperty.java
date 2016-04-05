package io.reactivesprint.rx;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.SubscriptionList;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * A mutable property of type {@code Value} that allows observation of its changes.
 * Inspired by [ReactiveCocoa 4](https://github.com/ReactiveCocoa/ReactiveCocoa)
 */
public final class MutableProperty<Value> implements IMutableProperty<Value> {
    //region Fields

    private Value value;
    private final Object lock = new Object();
    private final Subject<Value, Value> valueSubject;
    private final Observable<Value> valueObservable;

    //endregion

    //region Constructors

    /**
     * Initializes the property with {@code initialValue.}
     */
    public MutableProperty(Value initialValue) {
        this.value = initialValue;
        valueSubject = BehaviorSubject.create(initialValue);
        valueObservable = valueSubject.asObservable();
    }

    //endregion

    //region PropertyType

    @Override
    public Value getValue() {
        synchronized (lock) {
            return value;
        }
    }

    @Override
    public Observable<Value> getObservable() {
        return valueObservable;
    }

    //endregion

    //region Setter

    @Override
    public void setValue(Value value) {
        synchronized (lock) {
            this.value = value;
        }
        valueSubject.onNext(value);
    }

    //endregion

    //region Binding

    public static <Value> Subscription bind(/* this */ final IMutableProperty<Value> destination, Observable<Value> source) {
        final SubscriptionList subscriptionList = new SubscriptionList();

        destination.getObservable().subscribe(new Observer<Value>() {
            @Override
            public void onCompleted() {
                subscriptionList.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Value value) {

            }
        });

        subscriptionList.add(source.subscribe(new Subscriber<Value>() {
            @Override
            public void onCompleted() {
                subscriptionList.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Value value) {
                destination.setValue(value);
            }
        }));

        return subscriptionList;
    }

    public static <Value> Subscription bind(/* this */ final IMutableProperty<Value> destination, IProperty<Value> source) {
        return destination.bind(source.getObservable());
    }

    @Override
    public Subscription bind(Observable<Value> source) {
        return bind(this, source);
    }

    @Override
    public Subscription bind(IProperty<Value> source) {
        return bind(this, source);
    }

    //endregion

    //region Finalize

    @Override
    protected void finalize() throws Throwable {
        valueSubject.onCompleted();
        super.finalize();
    }

    //endregion
}
