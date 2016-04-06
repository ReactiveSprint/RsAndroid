package io.reactivesprint.views;

import io.reactivesprint.viewmodels.ViewModelType;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents any View in {@code MVVM}.
 */
public interface IView<VM extends ViewModelType> {
    /**
     * @return ViewModel used in the receiver.
     */
    VM getViewModel();

    /**
     * Binds {@code viewModel} to the receiver.
     */
    void bindViewModel(VM viewModel);

    /**
     * Binds {@link ViewModelType#getActive()} property from the receiver.
     */
    void bindActive(VM viewModel);
}
