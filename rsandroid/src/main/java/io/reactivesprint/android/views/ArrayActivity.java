package io.reactivesprint.android.views;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IViewBinder;

public abstract class ArrayActivity<E extends IAndroidViewModel, VM extends IArrayViewModel<E> & IAndroidViewModel>
        extends RsActivity<VM>
        implements IArrayView<E, VM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new ArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, ActivityEvent.START));
    }
}
