package io.reactivesprint.android.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.trello.rxlifecycle.RxLifecycle;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IArrayViewController;
import io.reactivesprint.views.IFetchedArrayViewController;
import rx.Observable;
import rx.Subscription;
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
            A extends IActivity<VM> & IArrayViewController<VM, AVM>> Subscription bindCount(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.count().getObservable(), onDataSetChanged(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            A extends IActivity<VM> & IArrayViewController<VM, AVM>> Subscription bindLocalizedEmptyMessage(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.localizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            A extends IActivity<VM> & IFetchedArrayViewController<VM, AVM>> Subscription bindRefreshing(final A activity, AVM arrayViewModel) {
        return bind(activity, arrayViewModel.refreshing().getObservable(), presentRefreshing(activity));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            A extends IActivity<VM> & IFetchedArrayViewController<VM, AVM>> Subscription bindFetchingNextPage(final A activity, AVM arrayViewModel) {
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
            F extends IFragment<VM> & IArrayViewController<VM, AVM>> Subscription bindCount(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.count().getObservable(), onDataSetChanged(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            AVM extends IArrayViewModel<E> & IAndroidViewModel,
            F extends IFragment<VM> & IArrayViewController<VM, AVM>> Subscription bindLocalizedEmptyMessage(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.localizedEmptyMessage().getObservable(), setLocalizedEmptyMessage(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, AVM>> Subscription bindRefreshing(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.refreshing().getObservable(), presentRefreshing(fragment));
    }

    public static <VM extends IAndroidViewModel,
            E extends IAndroidViewModel,
            P, FI, FO,
            AVM extends IFetchedArrayViewModel<E, P, FI, FO> & IAndroidViewModel,
            F extends IFragment<VM> & IFetchedArrayViewController<VM, AVM>> Subscription bindFetchingNextPage(final F fragment, AVM arrayViewModel) {
        return bind(fragment, arrayViewModel.refreshing().getObservable(), presentFetchingNextPage(fragment));
    }

    //endregion
}
