package io.reactivesprint.views;

import io.reactivesprint.viewmodels.FetchedArrayViewModelType;
import io.reactivesprint.viewmodels.ViewModelType;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents a View Controller that wraps {@link FetchedArrayViewModelType}
 */
public interface IFetchedArrayViewController<VM extends ViewModelType, AVM extends FetchedArrayViewModelType> extends IArrayViewController<VM, AVM> {
    /**
     * Binds {@link FetchedArrayViewModelType#isRefreshing()} to {@link #presentRefreshing(boolean)}
     */
    void bindRefreshing(AVM arrayViewModel);

    /**
     * Shows or hides a view representing "refreshing."
     */
    void presentRefreshing(boolean refreshing);

    /**
     * Binds {@link FetchedArrayViewModelType#isFetchingNextPage()} to {@link #presentFetchingNextPage(boolean)}
     */
    void bindFetchingNextPage(AVM arrayViewModel);

    /**
     * Shows or hides a view representing "fetchingNextPage."
     */
    void presentFetchingNextPage(boolean fetchingNextPage);
}
