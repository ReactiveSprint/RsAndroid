package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public class FetchedArrayViewBinder<E extends IViewModel, VM extends IFetchedArrayViewModel<E, ?, ?, ?>>
        extends ArrayViewBinder<E, VM> implements IFetchedArrayViewBinder<E, VM> {
    public FetchedArrayViewBinder(IFetchedArrayView<E, VM> view, ILifecycleProvider<?> lifecycleProvider) {
        super(view, lifecycleProvider);
    }

    @Override
    public IFetchedArrayView<E, VM> getView() {
        return (IFetchedArrayView<E, VM>) super.getView();
    }

    @Override
    protected SubscriptionList bindViewModel() {
        SubscriptionList subscription = super.bindViewModel();

        VM arrayViewModel = getView().getViewModel();

        if (arrayViewModel == null) {
            return null;
        }

        if (subscription == null) {
            subscription = new SubscriptionList();
        }

        subscription.add(bindRefreshing(arrayViewModel));
        subscription.add(bindFetchingNextPage(arrayViewModel));

        return subscription;
    }


    @Override
    public Subscription bindRefreshing(VM arrayViewModel) {
        return arrayViewModel.refreshing().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.presentRefreshing(getView()));
    }

    @Override
    public Subscription bindFetchingNextPage(VM arrayViewModel) {
        return arrayViewModel.fetchingNextPage().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.presentFetchingNextPage(getView()));
    }
}
