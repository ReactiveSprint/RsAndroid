package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public class ArrayViewBinder<E extends IViewModel, VM extends IArrayViewModel<E>>
        extends ViewBinder<VM> implements IArrayViewBinder<E, VM> {
    public ArrayViewBinder(IArrayView<E, VM> view, ILifecycleProvider<?> lifecycleProvider) {
        super(view, lifecycleProvider);
    }

    @Override
    public IArrayView<E, VM> getView() {
        return (IArrayView<E, VM>) super.getView();
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

        subscription.add(bindCount(arrayViewModel));
        subscription.add(bindLocalizedEmptyMessage(arrayViewModel));
        subscription.add(bindLocalizedEmptyMessageVisibility(arrayViewModel));

        return subscription;
    }

    @Override
    public Subscription bindCount(VM arrayViewModel) {
        return arrayViewModel.count().getObservable()
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(Views.onDataSetChanged(getView()));
    }

    @Override
    public Subscription bindLocalizedEmptyMessage(VM arrayViewModel) {
        return arrayViewModel.localizedEmptyMessage().getObservable()
                .compose(this.<CharSequence>bindToLifecycle())
                .subscribe(Views.setLocalizedEmptyMessage(getView()));
    }

    @Override
    public Subscription bindLocalizedEmptyMessageVisibility(VM arrayViewModel) {
        return arrayViewModel.empty().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(Views.setLocalizedEmptyMessageVisibility(getView()));
    }
}
