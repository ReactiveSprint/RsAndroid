package io.reactivesprint.android.views.support;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidViewControllers;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IFetchedArrayViewController;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class FetchedArrayFragment<VM extends IAndroidViewModel, AVM extends IFetchedArrayViewModel & IAndroidViewModel> extends ArrayFragment<VM, AVM> implements
        IFetchedArrayViewController<VM, AVM> {
    @Override
    public void onStart() {
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
