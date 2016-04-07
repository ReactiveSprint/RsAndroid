package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller. This is useful for implementing Fragments or Activities in Android.
 */
public interface IViewController<VM extends IViewModel> extends IView<VM> {
    /**
     * Binds {@link IViewModel#getTitle()} to the receiver title.
     */
    void bindTitle(VM viewModel);

    /**
     * Sets Title of View Controller.
     */
    void setTitle(CharSequence title);

    /**
     * Binds {@link IViewModel#getLoading()} to {@link #presentLoading(boolean)}
     */
    void bindLoading(VM viewModel);

    /**
     * Shows or hides a view that represents loading.
     */
    void presentLoading(boolean loading);

    /**
     * Binds {@link IViewModel#getErrors()} to {@link #presentError(IViewModelException)}
     */
    void bindErrors(VM viewModel);

    /**
     * Presents `error`
     */
    void presentError(IViewModelException error);
}
