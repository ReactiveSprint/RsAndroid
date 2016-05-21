package io.reactivesprint.android.views;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IFetchedArrayView;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayActivity<VM extends IAndroidViewModel, AVM extends IFetchedArrayViewModel & IAndroidViewModel> extends ArrayActivity<VM, AVM>
        implements IFetchedArrayView<VM, AVM> {
    @Override
    protected void onStart() {
        super.onStart();
        AVM arrayViewModel = getArrayViewModel();
        if (arrayViewModel == null) {
            return;
        }
        bindRefreshing(arrayViewModel);
        bindFetchingNextPage(arrayViewModel);
    }

    @Override
    public void bindRefreshing(AVM arrayViewModel) {
        AndroidViewControllers.bindRefreshing(this, arrayViewModel);
    }

    @Override
    public void bindFetchingNextPage(AVM arrayViewModel) {
        AndroidViewControllers.bindFetchingNextPage(this, arrayViewModel);
    }
}
