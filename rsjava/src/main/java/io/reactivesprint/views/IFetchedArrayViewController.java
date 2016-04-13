package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link IFetchedArrayViewModel}
 */
public interface IFetchedArrayViewController
        <VM extends IViewModel, AVM extends IFetchedArrayViewModel>
        extends IArrayViewController<VM, AVM> {
    /**
     * Binds {@link IFetchedArrayViewModel#isRefreshing()} to {@link #presentRefreshing(boolean)}
     */
    void bindRefreshing(AVM arrayViewModel);

    /**
     * Shows or hides a view representing "refreshing."
     */
    void presentRefreshing(boolean refreshing);

    /**
     * Binds {@link IFetchedArrayViewModel#isFetchingNextPage()} to {@link #presentFetchingNextPage(boolean)}
     */
    void bindFetchingNextPage(AVM arrayViewModel);

    /**
     * Shows or hides a view representing "fetchingNextPage."
     */
    void presentFetchingNextPage(boolean fetchingNextPage);
}
