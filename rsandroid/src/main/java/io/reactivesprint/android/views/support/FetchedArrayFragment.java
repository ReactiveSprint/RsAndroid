package io.reactivesprint.android.views.support;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayFragment<VM extends IAndroidViewModel, AVM extends IFetchedArrayViewModel<? extends IAndroidViewModel, ?, ?, ?> & IAndroidViewModel>
        extends ArrayFragment<VM, AVM>
        implements IFetchedArrayView<VM, AVM> {
    @Override
    protected IViewBinder<VM, ? extends IView<VM>> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }
}
