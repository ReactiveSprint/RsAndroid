package io.reactivesprint.android.components;

import io.reactivesprint.android.AndroidViewControllers;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.views.IArrayViewController;

public abstract class ArrayActivity<VM extends IViewModel, E extends IViewModel, AVM extends IArrayViewModel<E>> extends RsActivity<VM> implements IArrayViewController<VM, E, AVM> {
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
