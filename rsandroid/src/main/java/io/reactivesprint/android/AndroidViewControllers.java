package io.reactivesprint.android;

import com.trello.rxlifecycle.RxLifecycle;

import io.reactivesprint.viewmodels.ArrayViewModelType;
import io.reactivesprint.viewmodels.FetchedArrayViewModelType;
import io.reactivesprint.viewmodels.ViewModelType;
import io.reactivesprint.views.IArrayViewController;
import io.reactivesprint.views.IFetchedArrayViewController;
import rx.Observable;
import rx.functions.Action1;

import static io.reactivesprint.views.ViewControllers.onDataSetChanged;
import static io.reactivesprint.views.ViewControllers.presentError;
import static io.reactivesprint.views.ViewControllers.presentFetchingNextPage;
import static io.reactivesprint.views.ViewControllers.presentLoading;
import static io.reactivesprint.views.ViewControllers.presentRefreshing;
import static io.reactivesprint.views.ViewControllers.setTitle;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * "Extension" Methods to bind {@link io.reactivesprint.views.ViewControllers}
 */
public final class AndroidViewControllers {
    //region Constructors

    private AndroidViewControllers() {
        throw new IllegalStateException("no instances.");
    }

    //endregion

    //region Activity

    public static <VM extends ViewModelType, T> void bind(final IActivity<VM> activity, Observable<T> observable, Action1<T> bindAction) {
        observable.compose(RxLifecycle.<T>bindActivity(activity.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends ViewModelType> void bindTitle(final IActivity<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getTitle().getObservable(), setTitle(fragment));
    }

    public static <VM extends ViewModelType> void bindLoading(final IActivity<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getLoading().getObservable(), presentLoading(fragment));
    }

    public static <VM extends ViewModelType> void bindErrors(final IActivity<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getErrors(), presentError(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends ArrayViewModelType<?>,
            F extends IActivity<VM> & IArrayViewController<VM, AVM>> void bindCount(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.getCount().getObservable(), onDataSetChanged(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends FetchedArrayViewModelType<?, ?, ?, ?>,
            F extends IActivity<VM> & IFetchedArrayViewController<VM, AVM>> void bindRefreshing(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentRefreshing(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends FetchedArrayViewModelType<?, ?, ?, ?>,
            F extends IActivity<VM> & IFetchedArrayViewController<VM, AVM>> void bindFetchingNextPage(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentFetchingNextPage(fragment));
    }

    //endregion

    //region Fragments

    public static <VM extends ViewModelType, T> void bind(final IFragment<VM> fragment, Observable<T> observable, Action1<T> bindAction) {
        observable.compose(RxLifecycle.<T>bindFragment(fragment.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends ViewModelType> void bindTitle(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getTitle().getObservable(), setTitle(fragment));
    }

    public static <VM extends ViewModelType> void bindLoading(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getLoading().getObservable(), presentLoading(fragment));
    }

    public static <VM extends ViewModelType> void bindErrors(final IFragment<VM> fragment, VM viewModel) {
        bind(fragment, viewModel.getErrors(), presentError(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends ArrayViewModelType<?>,
            F extends IFragment<VM> & IArrayViewController<VM, AVM>> void bindCount(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.getCount().getObservable(), onDataSetChanged(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends FetchedArrayViewModelType<?, ?, ?, ?>,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, AVM>> void bindRefreshing(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentRefreshing(fragment));
    }

    public static <VM extends ViewModelType,
            AVM extends FetchedArrayViewModelType<?, ?, ?, ?>,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, AVM>> void bindFetchingNextPage(final F fragment, AVM arrayViewModel) {
        bind(fragment, arrayViewModel.isRefreshing().getObservable(), presentFetchingNextPage(fragment));
    }

    //endregion
}
