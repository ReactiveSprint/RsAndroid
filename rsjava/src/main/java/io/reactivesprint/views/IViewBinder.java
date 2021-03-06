package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import rx.Subscription;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Represents a helper object which binds {@link IViewModel} to an {@link IView}
 */
public interface IViewBinder<VM extends IViewModel> {
    /**
     * @return wrapped {@link IView}
     */
    IView<VM> getView();

    /**
     * Binds {@link IViewModel#active()} property to {@link }
     */
    Subscription bindActive(VM viewModel);

    /**
     * Binds {@link IViewModel#title()} to the receiver title.
     */
    Subscription bindTitle(VM viewModel);

    /**
     * Binds {@link IViewModel#loading()} to {@link IView#presentLoading(boolean)}
     */
    Subscription bindLoading(VM viewModel);

    /**
     * Binds {@link IViewModel#errors()} to {@link IView#presentError(IViewModelException)}
     */
    Subscription bindErrors(VM viewModel);
}
