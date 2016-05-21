package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * Represents a View in {@code MVVM} that supports setting ViewModel.
 */
public interface IViewHolder<VM extends IViewModel> extends IView {
    /**
     * Sets ViewModel to the receiver.
     */
    void setViewModel(VM viewModel);
}
