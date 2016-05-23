package io.reactivesprint.android.views.support;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class FetchedRecyclerFragment<VM extends IAndroidViewModel, E extends IAndroidViewModel, AVM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends RecyclerFragment<VM, E, AVM>
        implements IFetchedArrayView<VM, E, AVM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }

    @Override
    public void presentRefreshing(boolean refreshing) {

    }

    @Override
    public void presentFetchingNextPage(boolean fetchingNextPage) {

    }
}
