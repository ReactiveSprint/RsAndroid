package io.reactivesprint.android;

import com.trello.rxlifecycle.RxLifecycle;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.views.IArrayViewController;
import io.reactivesprint.views.IFetchedArrayViewController;
import rx.Observable;
import rx.functions.Action1;

import static io.reactivesprint.views.ViewControllers.onDataSetChanged;
import static io.reactivesprint.views.ViewControllers.presentError;
import static io.reactivesprint.views.ViewControllers.presentFetchingNextPage;
import static io.reactivesprint.views.ViewControllers.presentLoading;
import static io.reactivesprint.views.ViewControllers.presentRefreshing;
import static io.reactivesprint.views.ViewControllers.setLocalizedEmptyMessage;
import static io.reactivesprint.views.ViewControllers.setTitle;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * "Extension" Methods to bind {@link io.reactivesprint.views.ViewControllers}
 */
public final class AndroidViewControllers {
    //region Constructors

    private AndroidViewControllers() {
        throw new AssertionError("No instances.");
    }

    //endregion

    //region Activity

    public static <VM extends IViewModel, T> void bind(final IActivity<VM> activity, Observable<T> observable, Action1<T> bindAction) {
        observable.compose(RxLifecycle.<T>bindActivity(activity.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends IViewModel> void bindTitle(final IActivity<VM> activity, VM viewModel) {
        bind(activity, viewModel.getTitle().getObservable(), setTitle(activity));
    }

    public static <VM extends IViewModel> void bindLoading(final IActivity<VM> activity, VM viewModel) {
        bind(activity, viewModel.isLoading().getObservable(), presentLoading(activity));
    }

    public static <VM extends IViewModel> void bindErrors(final IActivity<VM> activity, VM viewModel) {
        bind(activity, viewModel.getErrors(), presentError(activity));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            AVM extends IArrayViewModel<E>,
            A extends IActivity<VM> & IArrayViewController<VM, E, AVM>> void bindCount(final A activity, AVM arrayViewModel) {
        bind(activity, arrayViewModel.getCount().getObservable(), onDataSetChanged(activity));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            AVM extends IArrayViewModel<E>,
            A extends IActivity<VM> & IArrayViewController<VM, E, AVM>> void bindLocalizedEmptyMessage(final A activity, AVM arrayViewModel) {
        bind(activity, arrayViewModel.getLocalizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(activity));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO>,
            A extends IActivity<VM> & IFetchedArrayViewController<VM, E, P, FI, FO, AVM>> void bindRefreshing(final A activity, AVM arrayViewModel) {
        bind(activity, arrayViewModel.isRefreshing().getObservable(), presentRefreshing(activity));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO>,
            A extends IActivity<VM> & IFetchedArrayViewController<VM, E, P, FI, FO, AVM>> void bindFetchingNextPage(final A activity, AVM arrayViewModel) {
        bind(activity, arrayViewModel.isRefreshing().getObservable(), presentFetchingNextPage(activity));
    }

    //endregion

    //region Fragments

    public static <VM extends IViewModel, T> void bind(final IFragment<VM> fragment, Observable<T> observable, Action1<T> bindAction) {
        observable.compose(RxLifecycle.<T>bindFragment(fragment.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends IViewModel> void bindTitle(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getTitle().getObservable(), setTitle(fragment));
    }

    public static <VM extends IViewModel> void bindLoading(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.isLoading().getObservable(), presentLoading(fragment));
    }

    public static <VM extends IViewModel> void bindErrors(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getErrors(), presentError(fragment));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            AVM extends IArrayViewModel<E>,
            F extends IFragment<VM> & IArrayViewController<VM, E, AVM>> void bindCount(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.getCount().getObservable(), onDataSetChanged(fragment));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            AVM extends IArrayViewModel<E>,
            F extends IFragment<VM> & IArrayViewController<VM, E, AVM>> void bindLocalizedEmptyMessage(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.getLocalizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(fragment));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO>,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, E, P, FI, FO, AVM>> void bindRefreshing(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentRefreshing(fragment));
    }

    public static <VM extends IViewModel,
            E extends IViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO>,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, E, P, FI, FO, AVM>> void bindFetchingNextPage(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentFetchingNextPage(fragment));
    }

    //endregion
}
