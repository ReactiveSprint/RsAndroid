package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public interface IArrayViewBinder<VM extends IViewModel, AVM extends IArrayViewModel, V extends IArrayView<VM, AVM>>
        extends IViewBinder<VM, V> {
    /**
     * Binds {@link IArrayViewModel#count()} to {@link IArrayView#onDataSetChanged()}
     */
    Subscription bindCount(AVM arrayViewModel);

    /**
     * Binds {@link IArrayViewModel#localizedEmptyMessage()} to {@link IArrayView#setLocalizedEmptyMessage(CharSequence)}
     */
    Subscription bindLocalizedEmptyMessage(AVM arrayViewModel);

    /**
     * Bind {@link IArrayViewModel#localizedEmptyMessage()} to {@link IArrayView#setLocalizedEmptyMessageVisibility(boolean)}
     */
    Subscription bindLocalizedEmptyMessageVisibility(AVM arrayViewModel);
}
