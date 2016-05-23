package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link IFetchedArrayViewModel}
 */
public interface IFetchedArrayView
        <E extends IViewModel, VM extends IFetchedArrayViewModel<E, ?, ?, ?>>
        extends IArrayView<E, VM> {
    /**
     * Shows or hides a view representing "refreshing."
     */
    void presentRefreshing(boolean refreshing);

    /**
     * Shows or hides a view representing "fetchingNextPage."
     */
    void presentFetchingNextPage(boolean fetchingNextPage);
}
