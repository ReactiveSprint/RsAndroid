package io.reactivesprint.android.recyclerview.support;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProviders;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class FetchedRecyclerFragment<E extends IAndroidViewModel, VM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends RecyclerFragment<E, VM>
        implements IFetchedArrayView<E, VM> {
    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProviders.from(this, FragmentEvent.START));
    }

    @Override
    public void presentRefreshing(boolean refreshing) {

    }

    @Override
    public void presentFetchingNextPage(boolean fetchingNextPage) {

    }
}
