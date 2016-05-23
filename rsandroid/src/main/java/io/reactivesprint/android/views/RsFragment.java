package io.reactivesprint.android.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.RxFragment;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;
import io.reactivesprint.views.ViewBinder;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RsFragment<VM extends IAndroidViewModel> extends RxFragment implements IView<VM> {
    //region Fields

    private VM viewModel;
    private IViewBinder<VM> viewBinder;

    //endregion

    public RsFragment() {
        viewBinder = onCreateViewBinder();
    }

    protected IViewBinder<VM> onCreateViewBinder() {
        return new ViewBinder<>(this, AndroidLifecycleProviders.from(this, FragmentEvent.START));
    }

    //TODO: Handle Destory

    //region LifeCycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VM viewModel = AndroidViews.getViewModelFromBundle(savedInstanceState);

        if (viewModel == null) {
            viewModel = AndroidViews.getViewModelFromBundle(getArguments());
        }

        if (viewModel != null && !viewModel.equals(getViewModel())) {
            viewModel.setContext(getActivity().getApplicationContext());
            setViewModel(viewModel);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AndroidViews.onSaveInstanceState(viewModel, outState);
    }

    //endregion

    //region Properties

    public IViewBinder<VM> getViewBinder() {
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
