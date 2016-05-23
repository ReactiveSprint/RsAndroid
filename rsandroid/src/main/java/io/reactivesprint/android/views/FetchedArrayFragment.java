package io.reactivesprint.android.views;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayFragment<VM extends IAndroidViewModel, E extends IAndroidViewModel, AVM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends ArrayFragment<VM, E, AVM>
        implements IFetchedArrayView<VM, E, AVM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }
}
