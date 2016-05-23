package io.reactivesprint.android.views.support;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayFragmentActivity<VM extends IAndroidViewModel, E extends IAndroidViewModel, AVM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends ArrayFragmentActivity<VM, E, AVM>
        implements IFetchedArrayView<VM, E, AVM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, ActivityEvent.START));
    }
}
