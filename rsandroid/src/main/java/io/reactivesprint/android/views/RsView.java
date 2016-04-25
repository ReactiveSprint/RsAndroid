package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import io.reactivesprint.rx.ICommand;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.SubscriptionList;

import static io.reactivesprint.Preconditions.checkNotNull;
import static io.reactivesprint.android.Preconditions.checkUiThread;

/**
 * Created by Ahmad Baraka on 4/19/16.
 * <p/>
 * Static methods to bind {@link ICommand} to views.
 */
public final class RsView {
    private RsView() {
        throw new AssertionError("No instances.");
    }

    /**
     * Invokes {@link #bindCommand(View, ICommand, Object)} with {@code null input}.
     *
     * @param view    View to bind its click.
     * @param command Command to forward clicks to.
     * @param <R>     Type of {@code command} Output.
     * @return a {@link Subscription} reference with which ends the binding.
     */
    public static <R> Subscription bindCommand(@NonNull View view, @NonNull final ICommand<Void, R> command) {
        return bindCommand(view, command, (Void) null);
    }

    /**
     * Invokes {@link #bindCommand(View, ICommand, Func1)} with constant {@code input}
     *
     * @param view    View to bind its click.
     * @param command Command to forward clicks to.
     * @param input   Input value which is always used to apply {@code command}.
     * @param <T>     Type of {@code command} Input.
     * @param <R>     Type of {@code command} Output.
     * @return a {@link Subscription} reference with which ends the binding.
     */
    public static <T, R> Subscription bindCommand(@NonNull View view, @NonNull ICommand<T, R> command, final T input) {
        return bindCommand(view, command, new Func1<Void, T>() {
            @Override
            public T call(Void aVoid) {
                return input;
            }
        });
    }

    /**
     * Invokes {@link ICommand#apply()} whenever {@link RxView#clicks(View)} sends a value.
     * And binds {@link ICommand#isEnabled()} to {@link RxView#enabled(View)}.
     * <br />
     * The binding ends when {@link RxView#detaches(View)} sends a value
     * or when {@link ICommand#isEnabled()} completes.
     *
     * @param view          View to bind its click.
     * @param command       Command to forward clicks to.
     * @param inputFunction {@link Func1} which maps {@link RxView#clicks(View)} emitted values
     *                      to other to {@code command} input values.
     * @param <T>           Type of {@code command} Input.
     * @param <R>           Type of {@code command} Output.
     * @return a {@link Subscription} reference with which ends the binding.
     */
    public static <T, R> Subscription bindCommand(@NonNull View view, final @NonNull ICommand<T, R> command,
                                                  @NonNull final Func1<Void, T> inputFunction) {
        checkNotNull(view, "view");
        checkNotNull(command, "command");
        checkNotNull(inputFunction, "inputFunction");
        checkUiThread();

        Observable<Void> untilObservable = RxView.detaches(view).take(1);

        final SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.add(command.isEnabled().getObservable()
                .takeUntil(untilObservable)
                .subscribe(RxView.enabled(view)));

        subscriptionList.add(RxView.clicks(view)
                .takeUntil(untilObservable)
                .takeUntil(command.getNotifications().ignoreElements())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        subscriptionList.add(command.apply(inputFunction.call(aVoid)).subscribe());
                    }
                }));

        return subscriptionList;
    }
}
