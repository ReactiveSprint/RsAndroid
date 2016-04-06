package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents any View in {@code MVVM}.
 */
public interface IView<VM extends IViewModel> {
    /**
     * @return ViewModel used in the receiver.
     */
    VM getViewModel();

    /**
     * Binds {@code viewModel} to the receiver.
     */
    void bindViewModel(VM viewModel);

    /**
     * Binds {@link IViewModel#getActive()} property from the receiver.
     */
    void bindActive(VM viewModel);
}
