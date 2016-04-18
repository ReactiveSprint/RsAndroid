package io.reactivesprint.android.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.trello.rxlifecycle.components.RxFragment;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModelException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RsFragment<VM extends IAndroidViewModel> extends RxFragment implements IFragment<VM> {
    //region Fields

    private VM viewModel;

    //endregion

    //region LifeCycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = AndroidViewControllers.getViewModelFromBundle(savedInstanceState);

        if (viewModel == null) {
            viewModel = AndroidViewControllers.getViewModelFromBundle(getArguments());
        }

        if (getViewModel() == null && viewModel != null) {
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
