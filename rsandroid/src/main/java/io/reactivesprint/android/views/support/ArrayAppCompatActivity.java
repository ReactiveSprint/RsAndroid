package io.reactivesprint.android.views.support;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public abstract class ArrayAppCompatActivity<VM extends IAndroidViewModel, AVM extends IArrayViewModel<? extends IAndroidViewModel> & IAndroidViewModel>
        extends RsAppCompatActivity<VM>
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
