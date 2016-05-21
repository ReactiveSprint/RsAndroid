package io.reactivesprint.android.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.RxFragment;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import rx.functions.Action1;
import rx.functions.Func1;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RsFragment<VM extends IAndroidViewModel> extends RxFragment implements IFragment<VM> {
    //region Fields

    private VM viewModel;

    //endregion

    public RsFragment() {
        lifecycle().filter(new Func1<FragmentEvent, Boolean>() {
            @Override
            public Boolean call(FragmentEvent fragmentEvent) {
                return FragmentEvent.START == fragmentEvent;
            }
        }).compose(this.<FragmentEvent>bindUntilEvent(FragmentEvent.STOP)).subscribe(new Action1<FragmentEvent>() {
            @Override
            public void call(FragmentEvent fragmentEvent) {
                VM viewModel = getViewModel();
                if (viewModel == null) {
                    return;
                }
                bindActive(viewModel);
                bindTitle(viewModel);
                bindLoading(viewModel);
                bindErrors(viewModel);
            }
        });

        lifecycle().filter(new Func1<FragmentEvent, Boolean>() {
            @Override
            public Boolean call(FragmentEvent fragmentEvent) {
                return FragmentEvent.DESTROY == fragmentEvent;
            }
        }).subscribe(new Action1<FragmentEvent>() {
            @Override
            public void call(FragmentEvent fragmentEvent) {
                viewModel = null;
            }
        });
    }

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
    public void onSaveInstanceState(Bundle outState) {
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
