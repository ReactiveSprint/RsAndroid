package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public class ArrayViewBinder<VM extends IViewModel, E extends IViewModel, AVM extends IArrayViewModel<E>>
        extends ViewBinder<VM> implements IArrayViewBinder<VM, E, AVM> {
    public ArrayViewBinder(IArrayView<VM, E, AVM> view, ILifecycleProvider<?> lifecycleProvider) {
        super(view, lifecycleProvider);
    }

    @Override
    public IArrayView<VM, E, AVM> getView() {
        return (IArrayView<VM, E, AVM>) super.getView();
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

        subscription.add(bindCount(arrayViewModel));
        subscription.add(bindLocalizedEmptyMessage(arrayViewModel));
        subscription.add(bindLocalizedEmptyMessageVisibility(arrayViewModel));

        return subscription;
    }

    @Override
    public Subscription bindCount(AVM arrayViewModel) {
        return arrayViewModel.count().getObservable()
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(Views.onDataSetChanged(getView()));
    }

    @Override
    public Subscription bindLocalizedEmptyMessage(AVM arrayViewModel) {
        return arrayViewModel.localizedEmptyMessage().getObservable()
                .compose(this.<CharSequence>bindToLifecycle())
                .subscribe(Views.setLocalizedEmptyMessage(getView()));
    }

    @Override
    public Subscription bindLocalizedEmptyMessageVisibility(AVM arrayViewModel) {
        return arrayViewModel.empty().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.setLocalizedEmptyMessageVisibility(getView()));
    }
}
