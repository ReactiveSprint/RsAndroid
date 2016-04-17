package io.reactivesprint.android.views;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.IArrayViewController;

public abstract class ArrayActivity<VM extends IAndroidViewModel, AVM extends IArrayViewModel & IAndroidViewModel> extends RsActivity<VM> implements IArrayViewController<VM, AVM> {
    @Override
    public AVM getArrayViewModel() {
        //noinspection unchecked
        return (AVM) getViewModel();
    }

    @Override
    public void bindCount(AVM arrayViewModel) {
        AndroidViewControllers.bindCount(this, arrayViewModel);
    }

    @Override
    public void bindLocalizedEmptyMessage(AVM arrayViewModel) {
        AndroidViewControllers.bindLocalizedEmptyMessage(this, arrayViewModel);
    }
}
