package io.reactivesprint.views;

import io.reactivesprint.viewmodels.ArrayViewModelType;
import io.reactivesprint.viewmodels.ViewModelType;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link ArrayViewModelType}
 */
public interface IArrayViewController<VM extends ViewModelType, AVM extends ArrayViewModelType> extends IViewController<VM> {
    /**
     * @return a ViewModel used as {@code ArrayViewModelType}
     * This could return a different value than {@link #getViewModel()}
     */
    AVM getArrayViewModel();

    /**
     * Binds {@link ArrayViewModelType#getCount()} to {@link #onDataSetChanged()}
     */
    void bindCount(AVM arrayViewModel);

    /**
     * Should be implemented to reload views.
     */
    void onDataSetChanged();

    /**
     * Binds {@link ArrayViewModelType#getLocalizedEmptyMessage()} to {@link #setLocalizedEmptyMessage(CharSequence)}
     */
    void bindLocalizedEmptyMessage(AVM arrayViewModel);

    /**
     * Sets message used when array is empty.
     */
    void setLocalizedEmptyMessage(CharSequence localizedEmptyMessage);
}
