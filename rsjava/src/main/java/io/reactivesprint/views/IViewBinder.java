package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.IViewModelException;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public interface IViewBinder<VM extends IViewModel, V extends IView<VM>> {
    /**
     * @return wrapped {@link IView}
     */
    V getView();

    /**
     * Binds {@link IViewModel#active()} property to {@link }
     */
    void bindActive(VM viewModel);

    /**
     * Binds {@link IViewModel#title()} to the receiver title.
     */
    void bindTitle(VM viewModel);

    /**
     * Binds {@link IViewModel#loading()} to {@link IView#presentLoading(boolean)}
     */
    void bindLoading(VM viewModel);

    /**
     * Binds {@link IViewModel#errors()} to {@link IView#presentError(IViewModelException)}
     */
    void bindErrors(VM viewModel);
}
