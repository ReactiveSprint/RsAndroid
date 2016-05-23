package io.reactivesprint.android.views;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;

public abstract class ArrayActivity<VM extends IAndroidViewModel, AVM extends IArrayViewModel<? extends IAndroidViewModel> & IAndroidViewModel>
        extends RsActivity<VM>
        implements IArrayView<VM, AVM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new ArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, ActivityEvent.START));
    }

    @SuppressWarnings("unchecked")
    @Override
    public AVM getArrayViewModel() {
        return (AVM) getViewModel();
    }
}
