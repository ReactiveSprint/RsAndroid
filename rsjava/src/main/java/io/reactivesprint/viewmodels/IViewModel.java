package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents a ViewModel.
 */
public interface IViewModel {
    /**
     * @return Whether the receiver is active.
     */
    IMutableProperty<Boolean> getActive();

    /**
     * @return General title of the receiver.
     */
    IProperty<CharSequence> getTitle();

    /**
     * @return Whether the receiver is loading.
     */
    IProperty<Boolean> isLoading();

    /**
     * @return Whether the receiver is enabled.
     */
    IProperty<Boolean> isEnabled();

    /**
     * An Observable of all errors that occur in the receiver.
     */
    Observable<IViewModelException> getErrors();

    /**
     * Binds {@code loadingObservable} to the receiver.
     *
     * @param loadingObservable An Observable which sends {@code true} if loading, {@code false} otherwise.
     */
    void bindLoading(Observable<Boolean> loadingObservable);

    /**
     * Binds {@code errorsObservable} to the receiver.
     *
     * @param errorObservable An Observable which sends {@link IViewModelException}.
     */
    void bindErrors(Observable<IViewModelException> errorObservable);

    /**
     * Binds {@code command} executing and errors of {@code command} to the receiver.
     * <p/>
     * If command errors are not instanceof {@link IViewModelException}, these errors will not be forward from the receiver.
     */
    <I, R> void bindCommand(ICommand<I, R> command);
}
