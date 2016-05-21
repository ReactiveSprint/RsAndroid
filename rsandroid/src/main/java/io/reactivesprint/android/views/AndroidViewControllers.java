package io.reactivesprint.android.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.trello.rxlifecycle.RxLifecycle;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.Views;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import static io.reactivesprint.views.Views.onDataSetChanged;
import static io.reactivesprint.views.Views.presentError;
import static io.reactivesprint.views.Views.presentFetchingNextPage;
import static io.reactivesprint.views.Views.presentLoading;
import static io.reactivesprint.views.Views.presentRefreshing;
import static io.reactivesprint.views.Views.setLocalizedEmptyMessage;
import static io.reactivesprint.views.Views.setLocalizedEmptyMessageVisibility;
import static io.reactivesprint.views.Views.setTitle;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * "Extension" Methods to bind {@link Views}
 */
public final class AndroidViewControllers {
    public static final String VIEWMODEL_KEY = AndroidViewControllers.class.getPackage().getName() + ".VIEWMODEL";

    //region Constructors

    private AndroidViewControllers() {
        throw new AssertionError("No instances.");
    }

    //endregion

    //region Bundle

    public static <VM extends IAndroidViewModel> void onSaveInstanceState(VM viewModel, Bundle outState) {
        outState.putParcelable(AndroidViewControllers.VIEWMODEL_KEY, viewModel);
    }

    public static <VM extends IAndroidViewModel> VM getViewModelFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return null;
        }
        VM viewModel = savedInstanceState.getParcelable(AndroidViewControllers.VIEWMODEL_KEY);
        if (viewModel != null) {
            return viewModel;
        }
        return null;
    }

    //endregion

    //region Activity

    public static <VM extends IAndroidViewModel, A extends Activity & IActivity<VM>>
    Intent getActivityIntent(Context context, Class<A> activityClass, VM viewModel) {
        Intent intent = new Intent(context, activityClass);

        intent.putExtra(VIEWMODEL_KEY, viewModel);

        return intent;
    }

    public static <VM extends IAndroidViewModel, T> Subscription bind(final IActivity<VM> activity, Observable<T> observable, Action1<T> bindAction) {
        return observable.compose(RxLifecycle.<T>bindActivity(activity.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends IAndroidViewModel> Subscription bindTitle(final IActivity<VM> activity, VM viewModel) {
        return bind(activity, viewModel.title().getObservable(), setTitle(activity));
    }

    public static <VM extends IAndroidViewModel> Subscription bindLoading(final IActivity<VM> activity, VM viewModel) {
        return bind(activity, viewModel.loading().getObservable(), presentLoading(activity));
    }

    public static <VM extends IAndroidViewModel> Subscription bindErrors(final IActivity<VM> activity, VM viewModel) {
        return bind(activity, viewModel.errors(), presentError(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            A extends IActivity<VM> & IArrayView<VM, AVM>> Subscription bindCount(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.count().getObservable(), onDataSetChanged(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            A extends IActivity<VM> & IArrayView<VM, AVM>> Subscription bindLocalizedEmptyMessage(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.localizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            A extends IActivity<VM> & IArrayView<VM, AVM>> Subscription bindLocalizedEmptyMessageVisibility(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.empty().getObservable(), setLocalizedEmptyMessageVisibility(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            A extends IActivity<VM> & IFetchedArrayView<VM, AVM>> Subscription bindRefreshing(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.refreshing().getObservable(), presentRefreshing(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            A extends IActivity<VM> & IFetchedArrayView<VM, AVM>> Subscription bindFetchingNextPage(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.refreshing().getObservable(), presentFetchingNextPage(activity));
    }

    //endregion

    //region Fragments

    public static <VM extends IAndroidViewModel, T> Subscription bind(final IFragment<VM> fragment, Observable<T> observable, Action1<T> bindAction) {
        return observable.compose(RxLifecycle.<T>bindFragment(fragment.lifecycle()))
                .subscribe(bindAction);
    }

    public static <VM extends IAndroidViewModel> Subscription bindTitle(final IFragment<VM> fragment, VM viewModel) {
        return bind(fragment, viewModel.title().getObservable(), setTitle(fragment));
    }

    public static <VM extends IAndroidViewModel> Subscription bindLoading(final IFragment<VM> fragment, VM viewModel) {
        return bind(fragment, viewModel.loading().getObservable(), presentLoading(fragment));
    }

    public static <VM extends IAndroidViewModel> Subscription bindErrors(final IFragment<VM> fragment, VM viewModel) {
        return bind(fragment, viewModel.errors(), presentError(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            F extends IFragment<VM> & IArrayView<VM, AVM>> Subscription bindCount(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.count().getObservable(), onDataSetChanged(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            F extends IFragment<VM> & IArrayView<VM, AVM>> Subscription bindLocalizedEmptyMessage(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.localizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            F extends IFragment<VM> & IArrayView<VM, AVM>> Subscription bindLocalizedEmptyMessageVisibility(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.empty().getObservable(), setLocalizedEmptyMessageVisibility(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            F extends IFragment<VM> & IFetchedArrayView<VM, AVM>> Subscription bindRefreshing(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.refreshing().getObservable(), presentRefreshing(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            F extends IFragment<VM> & IFetchedArrayView<VM, AVM>> Subscription bindFetchingNextPage(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.refreshing().getObservable(), presentFetchingNextPage(fragment));
    }

    //endregion
}
