package io.reactivesprint.rx;

import java.util.Arrays;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static io.reactivesprint.internal.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/30/15.
 * Represents a Command or Action that will do some work when executed with {@code I}
 * and returns {@code R.}
 *
 * @see #apply(Object[])
 */
public final class Command<I, R> implements ICommand<I, R> {
    //region Fields

    private final Func1<I, Observable<R>> createObservable;

    private final Subject<Notification<R>, Notification<R>> notifications = PublishSubject.create();

    private final Observable<R> values;
    private final Observable<Throwable> errors;

    private final MutableProperty<Boolean> executing = new MutableProperty<>(false);
    private final IProperty<Boolean> enabled;

    private final Object lock = new Object();

    //endregion

    //region Constructors

    /**
     * Creates a Command.
     *
     * @param createObservable Function used to create an Observable each time {@link #apply(Object[])} is invoked.
     */
    public Command(final Func1<I, Observable<R>> createObservable) {
        this(new ConstantProperty<>(true), createObservable);
    }

    /**
     * Creates a Command.
     *
     * @param enabled          Property whether or not this Command should be enabled.
     * @param createObservable Function used to create an Observable each time {@link #apply(Object[])} is invoked.
     */
    public Command(IProperty<Boolean> enabled,
                   final Func1<I, Observable<R>> createObservable) {
        checkNotNull(createObservable, "createObservable");
        this.createObservable = createObservable;

        Observable<Notification<R>> notificationObservable = notifications;

        values = notificationObservable.filter(new Func1<Notification<R>, Boolean>() {
            @Override
            public Boolean call(Notification<R> outputNotification) {
                return outputNotification.isOnNext();
            }
        }).dematerialize();

        errors = notificationObservable.filter(new Func1<Notification<R>, Boolean>() {
            @Override
            public Boolean call(Notification<R> outputNotification) {
                return outputNotification.isOnError();
            }
        }).map(new Func1<Notification<R>, Throwable>() {
            @Override
            public Throwable call(Notification<R> outputNotification) {
                return outputNotification.getThrowable();
            }
        });

        this.enabled = new Property<>(false, Observable.combineLatest(enabled.getObservable(), isExecuting().getObservable(), new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean userEnabled, Boolean executing) {
                return userEnabled && !executing;
            }
        }));
    }

    //endregion

    //region Apply

    /**
     * Applies the receiver returning an {@link Observable} which when subscribed,
     * will execute the command with {@code input} and forwards the results.
     * <p/>
     * If Command is executing or not enabled,
     * an Observable with error {@link CommandNotEnabledException} is returned.
     *
     * @param inputs Must be either null, empty or contain exactly 1 object.
     */
    @SafeVarargs
    public final Observable<R> apply(I... inputs) {
        if (inputs != null && inputs.length > 1) {
            throw new IllegalStateException("Invalid value of inputs. It must be either null, empty or contain exactly 1 object. Received value is " + Arrays.toString(inputs));
        }

        final I input = inputs != null && inputs.length > 0 ? inputs[0] : null;
        return Observable.create(new Observable.OnSubscribe<R>() {
            @Override
            public void call(final Subscriber<? super R> subscriber) {
                boolean startedExecuting = false;

                synchronized (lock) {
                    if (enabled.getValue()) {
                        executing.setValue(true);
                        startedExecuting = true;
                    }
                }

                if (!startedExecuting) {
                    subscriber.onError(new CommandNotEnabledException());
                    return;
                }

                ConnectableObservable<R> connectableObservable = createObservable.call(input).publish();

                Subscription subscription = connectableObservable.materialize().subscribe(new Action1<Notification<R>>() {
                    @Override
                    public void call(Notification<R> outputNotification) {
                        notifications.onNext(outputNotification);
                    }
                });
                subscriber.add(subscription);

                subscriber.add(connectableObservable.subscribe(subscriber));

                subscriber.add(new Subscription() {
                    @Override
                    public void unsubscribe() {
                        executing.setValue(false);
                    }

                    @Override
                    public boolean isUnsubscribed() {
                        return !executing.getValue();
                    }
                });

                connectableObservable.connect();
            }
        });
    }

    //endregion

    //region Getters

    /**
     * An Observable of all {@link Notification} that occur in the receiver.
     */
    public Observable<Notification<R>> getNotifications() {
        return notifications.asObservable();
    }

    /**
     * An Observable of Values generated from applications of the receiver.
     */
    public Observable<R> getValues() {
        return values;
    }

    /**
     * Whether or not the receiver is enabled.
     */
    public IProperty<Boolean> isEnabled() {
        return enabled;
    }

    /**
     * Whether or not the receiver is executing.
     */
    public IProperty<Boolean> isExecuting() {
        return new Property<>(executing);
    }

    /**
     * An Observable of all errors that occur in the receiver.
     */
    public Observable<Throwable> getErrors() {
        return errors;
    }

    //endregion

    //region Finalize

    @Override
    protected void finalize() throws Throwable {
        notifications.onCompleted();
        super.finalize();
    }

    //endregion
}
