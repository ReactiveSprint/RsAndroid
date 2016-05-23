package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public class FetchedArrayViewBinder<VM extends IViewModel, AVM extends IFetchedArrayViewModel<? extends IViewModel, ?, ?, ?>>
        extends ArrayViewBinder<VM, AVM> implements IFetchedArrayViewBinder<VM, AVM> {
    public FetchedArrayViewBinder(IFetchedArrayView<VM, AVM> view, ILifecycleProvider<?> lifecycleProvider) {
        super(view, lifecycleProvider);
    }

    @Override
    public IFetchedArrayView<VM, AVM> getView() {
        return (IFetchedArrayView<VM, AVM>) super.getView();
    }

    @Override
    protected SubscriptionList bindViewModel() {
        SubscriptionList subscription = super.bindViewModel();

        AVM arrayViewModel = getView().getArrayViewModel();

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
    public Subscription bindRefreshing(AVM arrayViewModel) {
        return arrayViewModel.refreshing().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.presentRefreshing(getView()));
    }

    @Override
    public Subscription bindFetchingNextPage(AVM arrayViewModel) {
        return arrayViewModel.fetchingNextPage().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.presentFetchingNextPage(getView()));
    }
}
