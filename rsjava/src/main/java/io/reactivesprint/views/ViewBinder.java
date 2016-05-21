package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

import static io.reactivesprint.Preconditions.checkNotNull;
import static io.reactivesprint.views.Views.presentError;
import static io.reactivesprint.views.Views.presentLoading;
import static io.reactivesprint.views.Views.setTitle;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Implementation of {@link IViewBinder} which uses {@link LifecycleProvider} to
 * start and stop binding.
 */
public class ViewBinder<VM extends IViewModel, V extends IView<VM>> implements IViewBinder<VM, V> {
    //region Fields

    private final V view;
    private final LifecycleProvider<?> lifecycleProvider;
    private final SubscriptionList subscriptionList = new SubscriptionList();

    //endregion

    //region Constructors

    public ViewBinder(V view, LifecycleProvider<?> lifecycleProvider) {
        checkNotNull(view, "view");
        checkNotNull(lifecycleProvider, "lifecycleProvider");
        this.view = view;
        this.lifecycleProvider = lifecycleProvider;

        lifecycleProvider.onStartBinding()
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        subscriptionList.unsubscribe();
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        Subscription subscription = bindViewModel();
                        if (subscription != null) {
                            subscriptionList.add(subscription);
                        }
                    }
                });
    }

    //endregion

    //region Helpers

    protected SubscriptionList bindViewModel() {
        VM viewModel = view.getViewModel();

        if (viewModel == null) {
            return null;
        }

        SubscriptionList subscription = new SubscriptionList();

        subscription.add(bindTitle(viewModel));
        subscription.add(bindLoading(viewModel));
        subscription.add(bindErrors(viewModel));

        return subscription;
    }

    public <T> Observable.Transformer<T, T> bindToLifecycle() {
        return lifecycleProvider.bindToLifecycle();
    }

    //endregion

    //region IViewBinder

    @Override
    public V getView() {
        return view;
    }


    public LifecycleProvider<?> getLifecycleProvider() {
        return lifecycleProvider;
    }

    @Override
    public Subscription bindActive(VM viewModel) {
        return null;
    }

    @Override
    public Subscription bindTitle(VM viewModel) {
        return viewModel.title().getObservable()
                .compose(this.<CharSequence>bindToLifecycle())
                .subscribe(setTitle(view));
    }

    @Override
    public Subscription bindLoading(VM viewModel) {
        return viewModel.loading().getObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(presentLoading(view));
    }

    @Override
    public Subscription bindErrors(VM viewModel) {
        return viewModel.errors()
                .compose(this.<IViewModelException>bindToLifecycle())
                .subscribe(presentError(view));
    }

    //endregion
}
