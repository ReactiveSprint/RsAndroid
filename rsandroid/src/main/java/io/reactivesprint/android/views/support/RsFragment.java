package io.reactivesprint.android.views.support;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxFragment;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidViewControllers;
import io.reactivesprint.android.views.IFragment;
import io.reactivesprint.viewmodels.IViewModelException;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public class RsFragment<VM extends IAndroidViewModel> extends RxFragment implements IFragment<VM> {
    //region Fields

    private VM viewModel;

    //endregion

    //region LifeCycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VM viewModel = AndroidViewControllers.getViewModelFromBundle(savedInstanceState);

        if (viewModel == null) {
            viewModel = AndroidViewControllers.getViewModelFromBundle(getArguments());
        }

        if (viewModel != null && !viewModel.equals(getViewModel())) {
            viewModel.setContext(getActivity().getApplicationContext());
            setViewModel(viewModel);
        }
    }

    @Override
    public void onStart() {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AndroidViewControllers.onSaveInstanceState(viewModel, outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel = null;
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
    public void setTitle(CharSequence title) {
        if (getActivity() == null) {
            return;
        }
        getActivity().setTitle(title);
    }

    @Override
    public void presentLoading(boolean loading) {

    }

    @Override
    public void presentError(IViewModelException error) {
    }

    //endregion
}
