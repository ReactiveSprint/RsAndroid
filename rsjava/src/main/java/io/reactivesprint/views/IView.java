package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.IViewModelException;

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
     * Sets Title of View Controller.
     */
    void setTitle(CharSequence title);

    /**
     * Shows or hides a view that represents loading.
     */
    void presentLoading(boolean loading);

    /**
     * Presents `error`
     */
    void presentError(IViewModelException error);
}
