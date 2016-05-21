package io.reactivesprint.android.views;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IFetchedArrayViewController;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class FetchedRecyclerFragment<VM extends IAndroidViewModel, AVM extends IFetchedArrayViewModel & IAndroidViewModel> extends RecyclerFragment<VM, AVM>
        implements IFetchedArrayViewController<VM, AVM> {
    @Override
    public void bindRefreshing(AVM arrayViewModel) {
        AndroidViewControllers.bindRefreshing(this, arrayViewModel);
    }

    @Override
    public void presentRefreshing(boolean refreshing) {

    }

    @Override
    public void bindFetchingNextPage(AVM arrayViewModel) {
        AndroidViewControllers.bindFetchingNextPage(this, arrayViewModel);
    }

    @Override
    public void presentFetchingNextPage(boolean fetchingNextPage) {

    }
}
