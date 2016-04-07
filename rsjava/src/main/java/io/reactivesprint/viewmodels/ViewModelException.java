package io.reactivesprint.viewmodels;

/**
 * Created by Ahmad Baraka on 4/7/16.
 * Implementation of {@link IViewModelException}.
 */
public class ViewModelException extends RuntimeException implements IViewModelException {
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
