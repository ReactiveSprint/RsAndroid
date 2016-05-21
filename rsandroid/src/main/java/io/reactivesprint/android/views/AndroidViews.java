package io.reactivesprint.android.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.Views;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * "Extension" Methods to bind {@link Views}
 */
public final class AndroidViews {
    public static final String VIEWMODEL_KEY = AndroidViews.class.getPackage().getName() + ".VIEWMODEL";

    //region Constructors

    private AndroidViews() {
        throw new AssertionError("No instances.");
    }

    //endregion

    public static <VM extends IAndroidViewModel> void onSaveInstanceState(VM viewModel, Bundle outState) {
        outState.putParcelable(AndroidViews.VIEWMODEL_KEY, viewModel);
    }

    public static <VM extends IAndroidViewModel> VM getViewModelFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return null;
        }
        VM viewModel = savedInstanceState.getParcelable(AndroidViews.VIEWMODEL_KEY);
        if (viewModel != null) {
            return viewModel;
        }
        return null;
    }

    public static <VM extends IAndroidViewModel, A extends Activity & IView<VM>>
    Intent getActivityIntent(Context context, Class<A> activityClass, VM viewModel) {
        Intent intent = new Intent(context, activityClass);

        intent.putExtra(VIEWMODEL_KEY, viewModel);

        return intent;
    }
}
