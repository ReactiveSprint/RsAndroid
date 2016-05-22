package io.reactivesprint.android.views;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;

public abstract class ArrayFragment<VM extends IAndroidViewModel, AVM extends IArrayViewModel<?> & IAndroidViewModel>
        extends RsFragment<VM> implements IArrayView<VM, AVM> {

    @SuppressWarnings("unchecked")
    @Override
    public AVM getArrayViewModel() {
        return (AVM) getViewModel();
    }

    @Override
    protected IViewBinder<VM, ? extends IView<VM>> onCreateViewBinder() {
        return new ArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }
}
