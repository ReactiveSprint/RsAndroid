package io.reactivesprint.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Ahmad Baraka on 5/30/15.
 * Represents a Command or Action that will do some work when executed with {@code Input}
 * and returns {@code Output.}
 *
 * @see #apply(Object)
 */
public final class Command<Input, Output> {
    //region Fields

    private final Scheduler scheduler;

    private final Func1<Input, Observable<Output>> createObservable;

    private final Subject<Notification<Output>, Notification<Output>> notifications = PublishSubject.create();

    private final Observable<Output> values;
    private final Observable<Throwable> errors;

    private final MutableProperty<Boolean> executing = new MutableProperty<>(false);
    private final PropertyType<Boolean> enabled;

    private final Object lock = new Object();

    //endregion

    //region Constructors

    /**
     * Creates a Command.
     *
     * @param scheduler        Scheduler used to send all notifications in the receiver.
     * @param createObservable Function used to create an Observable each time {@link #apply(Object)} is invoked.
     */
    public Command(@Nullable Scheduler scheduler, @NonNull final Func1<Input, Observable<Output>> createObservable) {
        this(scheduler, new ConstantProperty<>(true), createObservable);
    }

    /**
     * Creates a Command.
     *
     * @param scheduler        Scheduler used to send all notifications in the receiver.
     * @param enabled          Property whether or not this Command should be enabled.
     * @param createObservable Function used to create an Observable each time {@link #apply(Object)} is invoked.
     */
    public Command(@Nullable Scheduler scheduler, @NonNull PropertyType<Boolean> enabled,
                   @NonNull final Func1<Input, Observable<Output>> createObservable) {
        this.scheduler = scheduler;
        this.createObservable = createObservable;

        Observable<Notification<Output>> notificationObservable;
        if (scheduler == null) {
            notificationObservable = notifications;
        } else {
            notificationObservable = notifications.observeOn(scheduler);
        }

        values = notificationObservable.filter(new Func1<Notification<Output>, Boolean>() {
            @Override
            public Boolean call(Notification<Output> outputNotification) {
                return outputNotification.isOnNext();
            }
        }).dematerialize();

        errors = notificationObservable.filter(new Func1<Notification<Output>, Boolean>() {
            @Override
            public Boolean call(Notification<Output> outputNotification) {
                return outputNotification.isOnError();
            }
        }).map(new Func1<Notification<Output>, Throwable>() {
            @Override
            public Throwable call(Notification<Output> outputNotification) {
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
    public final Observable<Output> apply(Input... inputs) {
        if (inputs != null && inputs.length > 1) {
            throw new IllegalStateException("Invalid value of inputs. It must be either null, empty or contain exactly 1 object. Received value is " + Arrays.toString(inputs));
        }

        final Input input = inputs != null && inputs.length > 0 ? inputs[0] : null;
        return Observable.create(new Observable.OnSubscribe<Output>() {
            @Override
            public void call(Subscriber<? super Output> subscriber) {
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

                Subscription subscription = createObservable.call(input).materialize().subscribe(new Action1<Notification<Output>>() {
                    @Override
                    public void call(Notification<Output> outputNotification) {
                        notifications.onNext(outputNotification);
                    }
                });

                subscriber.add(subscription);
            }
        });
    }

    //endregion

    //region Getters

    /**
     * An Observable of all {@link Notification} that occur in the receiver.
     */
    public Observable<Notification<Output>> getNotifications() {
        return notifications.asObservable();
    }

    /**
     * An Observable of Values generated from applications of the receiver.
     */
    public Observable<Output> getValues() {
        return values;
    }

    /**
     * Whether or not the receiever is enabled.
     */
    public PropertyType<Boolean> isEnabled() {
        return enabled;
    }

    /**
     * Whether or not the receiever is executing.
     */
    public PropertyType<Boolean> isExecuting() {
        if (scheduler == null) {
            return new Property<>(executing);
        } else {
            return new Property<>(executing.getValue(), executing.getObservable().observeOn(scheduler));
        }
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

    //region CommandNotEnabledException

    /**
     * An Exception which occurs when user attempts to apply a Command while is not enabled.
     */
    public static class CommandNotEnabledException extends RuntimeException {
        public CommandNotEnabledException() {
            super("Command is not enabled, and cannot be executed.");
        }
    }

    //endregion
}
