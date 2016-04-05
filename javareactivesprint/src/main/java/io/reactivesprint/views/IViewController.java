package io.reactivesprint.views;

import io.reactivesprint.viewmodels.ErrorType;
import io.reactivesprint.viewmodels.ViewModelType;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller. This is useful for implementing Fragments or Activities in Android.
 */
public interface IViewController<VM extends ViewModelType> extends IView<VM> {
    /**
     * Binds {@link ViewModelType#getTitle()} to the receiver title.
     */
    void bindTitle(VM viewModel);

    /**
     * Binds {@link ViewModelType#getLoading()} to {@link #presentLoading(boolean)}
     */
    void bindLoading(VM viewModel);

    /**
     * Shows or hides a view that represents loading.
     */
    void presentLoading(boolean loading);

    /**
     * Binds {@link ViewModelType#getErrors()} to {@link #presentError(ErrorType)}
     */
    void bindErrors(VM viewModel);

    /**
     * Presents `error`
     */
    void presentError(ErrorType errorType);
}
