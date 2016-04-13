package io.reactivesprint.viewmodels;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/7/16.
 * Implementation of {@link IViewModelException}.
 */
public class ViewModelException extends RuntimeException implements IViewModelException {
    /**
     * Creates a {@link Func1} which maps sent {@code throwable} to {@link ViewModelException} instance.
     * <dl>
     * <dt><b>Example:</b></dt>
     * <dd>{@code observable.onErrorResumeNext(ViewModelException.<Integer>mapErrorFunc(message));}</dd>
     * </dl>
     */
    public static <T> Func1<Throwable, Observable<T>> mapErrorFunc() {
        return mapErrorFunc(null);
    }

    /**
     * Creates a {@link Func1} which maps sent {@code throwable} to {@link ViewModelException} instance.
     * <dl>
     * <dt><b>Example:</b></dt>
     * <dd>{@code observable.onErrorResumeNext(ViewModelException.<Integer>mapErrorFunc(message));}</dd>
     * </dl>
     */
    public static <T> Func1<Throwable, Observable<T>> mapErrorFunc(final String message) {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable<T> call(Throwable throwable) {
                ViewModelException exception;
                if (message == null) {
                    exception = new ViewModelException(throwable);
                } else {
                    exception = new ViewModelException(message, throwable);
                }
                return Observable.error(exception);
            }
        };
    }

    public ViewModelException(String message) {
        super(message);
    }

    public ViewModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewModelException(Throwable cause) {
        super(cause);
    }
}
