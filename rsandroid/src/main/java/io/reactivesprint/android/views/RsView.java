package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import io.reactivesprint.rx.ICommand;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/19/16.
 */
public final class RsView {
    private RsView() {
        throw new AssertionError("No instances.");
    }

    public static <R> void bindCommand(@NonNull View view, @NonNull final ICommand<Void, R> command) {
        bindCommand(view, command, (Void) null);
    }

    public static <T, R> void bindCommand(@NonNull View view, @NonNull ICommand<T, R> command, final T input) {
        bindCommand(view, command, new Func1<Void, T>() {
            @Override
            public T call(Void aVoid) {
                return input;
            }
        });
    }

    public static <T, R> void bindCommand(@NonNull View view, final @NonNull ICommand<T, R> command,
                                          @NonNull final Func1<Void, T> inputFunction) {
        checkNotNull(view, "view");
        checkNotNull(command, "command");
        checkNotNull(inputFunction, "inputFunction");

        Observable<Void> untilObservable = RxView.detaches(view).take(1);

        command.isEnabled().getObservable()
                .takeUntil(untilObservable)
                .subscribe(RxView.enabled(view));

        RxView.clicks(view)
                .takeUntil(untilObservable)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        command.apply(inputFunction.call(aVoid)).subscribe();
                    }
                });
    }
}
