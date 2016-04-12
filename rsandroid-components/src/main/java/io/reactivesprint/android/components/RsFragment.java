package io.reactivesprint.android.components;

import android.annotation.TargetApi;
import android.os.Build;

import com.trello.rxlifecycle.components.RxFragment;

import io.reactivesprint.android.AndroidViewControllers;
import io.reactivesprint.android.IFragment;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.IViewModel;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RsFragment<VM extends IViewModel> extends RxFragment implements IFragment<VM> {
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
    public void setTitle(CharSequence title) {
        getActivity().setTitle(title);
    }

    @Override
    public void presentLoading(boolean loading) {

    }

    @Override
    public void presentError(IViewModelException error) {
    }
}
