package io.reactivesprint.android.views.support;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidViewControllers;
import io.reactivesprint.android.views.IActivity;
import io.reactivesprint.viewmodels.IViewModelException;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public class RsFragmentActivity<VM extends IAndroidViewModel> extends RxFragmentActivity implements IActivity<VM> {
    //region Fields

    private VM viewModel;

    //endregion

    //region LifeCycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = AndroidViewControllers.getViewModelFromBundle(savedInstanceState);

        if (viewModel == null && getIntent() != null) {
            viewModel = AndroidViewControllers.getViewModelFromBundle(getIntent().getExtras());
        }

        if (getViewModel() == null && viewModel != null) {
            viewModel.setContext(getApplicationContext());
            setViewModel(viewModel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        VM viewModel = getViewModel();
        if (viewModel == null) {
            return;
        }
        bindActive(viewModel);
        bindTitle(viewModel);
        bindLoading(viewModel);
        bindErrors(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AndroidViewControllers.onSaveInstanceState(viewModel, outState);
    }

    //endregion

    //region Binding

    @Override
    public void bindActive(VM viewModel) {

    }

    @Override
    public void bindTitle(VM viewModel) {
        AndroidViewControllers.bindTitle(this, viewModel);
    }

    @Override
    public void bindLoading(VM viewModel) {
        AndroidViewControllers.bindLoading(this, viewModel);
    }

    @Override
    public void bindErrors(VM viewModel) {
        AndroidViewControllers.bindErrors(this, viewModel);
    }

    //endregion

    //region Properties

    @Override
    public VM getViewModel() {
        return viewModel;
    }

    protected void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    //endregion

    //region Presenting

    @Override
    public void presentLoading(boolean loading) {

    }

    @Override
    public void presentError(IViewModelException error) {
    }

    //endregion
}
