package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Subclass of {@link IArrayViewBinder} which binds an {@link IFetchedArrayViewModel} to an {@link IFetchedArrayView}
 */
public interface IFetchedArrayViewBinder
        <VM extends IViewModel, AVM extends IFetchedArrayViewModel<?, ?, ?, ?>, V extends IFetchedArrayView<VM, AVM>>
        extends IArrayViewBinder<VM, AVM, V> {
    /**
     * Binds {@link IFetchedArrayViewModel#refreshing()} to {@link IFetchedArrayView#presentRefreshing(boolean)}
     */
    Subscription bindRefreshing(AVM arrayViewModel);

    /**
     * Binds {@link IFetchedArrayViewModel#fetchingNextPage()} to {@link IFetchedArrayView#presentFetchingNextPage(boolean)}
     */
    Subscription bindFetchingNextPage(AVM arrayViewModel);
}
