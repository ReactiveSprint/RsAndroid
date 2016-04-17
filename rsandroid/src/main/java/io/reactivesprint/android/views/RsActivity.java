package io.reactivesprint.android.views;

import android.os.Bundle;

import com.trello.rxlifecycle.components.RxActivity;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.IViewModel;

public class RsActivity<VM extends IAndroidViewModel> extends RxActivity implements IActivity<VM> {
    private VM viewModel;

    @Override
    public VM getViewModel() {
        return viewModel;
    }

    //region Binding

    @Override
    public void bindViewModel(VM viewModel) {

    }

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

    @Override
    public void presentLoading(boolean loading) {

    }

    @Override
    public void presentError(IViewModelException error) {
    }
}
