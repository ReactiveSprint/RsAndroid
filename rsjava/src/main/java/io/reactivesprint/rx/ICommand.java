package io.reactivesprint.rx;

import rx.Notification;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/5/16.
 * Represents a Command or Action that will do some work when executed with {@code I}
 * and returns {@code R.}
 */
public interface ICommand<I, R> {

    /**
     * Invokes {@link #apply(Object)} with null.
     */
    Observable<R> apply();

    /**
     * Applies the receiver returning an {@link Observable} which when subscribed,
     * will execute the command with {@code input} and forwards the results.
     * <p/>
     * If Command is executing or not enabled,
     * an Observable with error {@link CommandNotEnabledException} is returned.
     */
    Observable<R> apply(I input);

    /**
     * An Observable of all {@link Notification} that occur in the receiver.
     */
    Observable<Notification<R>> getNotifications();

    /**
     * An Observable of Values generated from applications of the receiver.
     */
    Observable<R> getValues();

    /**
     * Whether or not the receiver is enabled.
     */
    IProperty<Boolean> isEnabled();

    /**
     * Whether or not the receiver is executing.
     */
    IProperty<Boolean> isExecuting();

    /**
     * An Observable of all errors that occur in the receiver.
     */
    Observable<Throwable> getErrors();
}
