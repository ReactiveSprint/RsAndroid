package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents a ViewModel.
 */
public interface ViewModelType {
    /**
     * @return Whether the receiver is active.
     */
    IMutableProperty<Boolean> getActive();

    /**
     * @return General title of the receiver.
     */
    IMutableProperty<String> getTitle();

    /**
     * @return Whether the receiver is loading.
     */
    IProperty<Boolean> getLoading();

    /**
     * @return Whether the receiver is enabled.
     */
    IProperty<Boolean> getEnabled();

    /**
     * An Observable of all errors that occur in the receiver.
     */
    Observable<ErrorType> getErrors();

    /**
     * Binds {@code loadingObservable} to the receiver.
     *
     * @param loadingObservable An Observable which sends {@code true} if loading, {@code false} otherwise.
     */
    void bindLoading(Observable<Boolean> loadingObservable);

    /**
     * Binds {@code errorsObservable} to the receiver.
     *
     * @param errorObservable An Observable which sends {@link ErrorType}.
     */
    void bindErrors(Observable<ErrorType> errorObservable);

    /**
     * Binds {@code command} executing and errors of {@code command} to the receiver.
     * <p/>
     * If command errors are not instanceof {@link ErrorType}, these errors will not be forward from the receiver.
     */
    <Input, Output> void bindCommand(Command<Input, Output> command);
}
