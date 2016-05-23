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
        <E extends IViewModel, VM extends IFetchedArrayViewModel<E, ?, ?, ?>>
        extends IArrayViewBinder<E, VM> {
    /**
     * @return wrapped {@link IFetchedArrayView}
     */
    IFetchedArrayView<E, VM> getView();

    /**
     * Binds {@link IFetchedArrayViewModel#refreshing()} to {@link IFetchedArrayView#presentRefreshing(boolean)}
     */
    Subscription bindRefreshing(VM arrayViewModel);

    /**
     * Binds {@link IFetchedArrayViewModel#fetchingNextPage()} to {@link IFetchedArrayView#presentFetchingNextPage(boolean)}
     */
    Subscription bindFetchingNextPage(VM arrayViewModel);
}
