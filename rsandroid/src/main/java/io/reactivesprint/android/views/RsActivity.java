package io.reactivesprint.android.views;

import android.os.Bundle;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;
import io.reactivesprint.views.ViewBinder;

public class RsActivity<VM extends IAndroidViewModel> extends RxActivity implements IView<VM> {
    //region Fields

    private VM viewModel;
    private IViewBinder<VM, ? extends IView<VM>> viewBinder;

    //endregion

    public RsActivity() {
        viewBinder = onCreateViewBinder();
    }

    protected IViewBinder<VM, ? extends IView<VM>> onCreateViewBinder() {
        return new ViewBinder<>(this, AndroidLifecycleProvider.from(this, ActivityEvent.START));
    }

    //region LifeCycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VM viewModel = AndroidViews.getViewModelFromBundle(savedInstanceState);

        if (viewModel == null && getIntent() != null) {
            viewModel = AndroidViews.getViewModelFromBundle(getIntent().getExtras());
        }

        if (viewModel != null && !viewModel.equals(getViewModel())) {
            viewModel.setContext(getApplicationContext());
            setViewModel(viewModel);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AndroidViews.onSaveInstanceState(viewModel, outState);
    }

    //endregion

    //region Properties

    public IViewBinder<VM, ? extends IView<VM>> getViewBinder() {
        return viewBinder;
    }

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
