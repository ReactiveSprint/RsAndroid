package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link IArrayViewModel}
 */
public interface IArrayView<E extends IViewModel, VM extends IArrayViewModel<E>> extends IView<VM> {
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
