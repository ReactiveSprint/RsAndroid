package io.reactivesprint.android.views.support;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidViewControllers;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.IArrayViewController;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public abstract class ArrayAppCompatActivity<VM extends IAndroidViewModel, AVM extends IArrayViewModel & IAndroidViewModel> extends RsAppCompatActivity<VM> implements IArrayViewController<VM, AVM> {
    @Override
    public AVM getArrayViewModel() {
        //noinspection unchecked
        return (AVM) getViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AVM arrayViewModel = getArrayViewModel();
        if (arrayViewModel == null) {
            return;
        }
        bindCount(arrayViewModel);
        bindLocalizedEmptyMessage(arrayViewModel);
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
