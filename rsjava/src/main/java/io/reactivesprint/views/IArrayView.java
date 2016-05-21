package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link IArrayViewModel}
 */
public interface IArrayView<VM extends IViewModel, AVM extends IArrayViewModel<?>> extends IView<VM> {
    /**
     * @return a ViewModel used as {@link IArrayViewModel}
     * This could return a different value than {@link #getViewModel()}
     */
    AVM getArrayViewModel();

    /**
     * Should be implemented to reload views.
     */
    void onDataSetChanged();

    /**
     * Sets message used when array is empty.
     */
    void setLocalizedEmptyMessage(CharSequence localizedEmptyMessage);

    /**
     * Sets visibility to empty message view.
     */
    void setLocalizedEmptyMessageVisibility(boolean visibility);
}
