package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Subclass of {@link IViewBinder} which binds an {@link IArrayViewModel} to an {@link IArrayView}
 */
public interface IArrayViewBinder<E extends IViewModel, VM extends IArrayViewModel<E>>
        extends IViewBinder<VM> {
    /**
     * @return wrapped {@link IArrayView}
     */
    IArrayView<E, VM> getView();

    /**
     * Binds {@link IArrayViewModel#count()} to {@link IArrayView#onDataSetChanged()}
     */
    Subscription bindCount(VM arrayViewModel);

    /**
     * Binds {@link IArrayViewModel#localizedEmptyMessage()} to {@link IArrayView#setLocalizedEmptyMessage(CharSequence)}
     */
    Subscription bindLocalizedEmptyMessage(VM arrayViewModel);

    /**
     * Bind {@link IArrayViewModel#localizedEmptyMessage()} to {@link IArrayView#setLocalizedEmptyMessageVisibility(boolean)}
     */
    Subscription bindLocalizedEmptyMessageVisibility(VM arrayViewModel);
}
