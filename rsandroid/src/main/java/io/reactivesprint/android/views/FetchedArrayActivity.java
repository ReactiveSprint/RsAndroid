package io.reactivesprint.android.views;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayActivity<VM extends IAndroidViewModel, E extends IAndroidViewModel, AVM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends ArrayActivity<VM, E, AVM>
        implements IFetchedArrayView<VM, E, AVM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, ActivityEvent.START));
    }
}
