package io.reactivesprint.android.views.support;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public abstract class ArrayFragment<VM extends IAndroidViewModel, AVM extends IArrayViewModel<?> & IAndroidViewModel>
        extends RsFragment<VM>
        implements IArrayView<VM, AVM> {
    @Override
    protected IViewBinder<VM, ? extends IView<VM>> onCreateViewBinder() {
        return new ArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }
}
