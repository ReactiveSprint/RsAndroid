package io.reactivesprint.android;

import android.view.View;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.views.IView;

public class ViewHolder<VM extends IViewModel> implements IView<VM> {
    private VM viewModel;
    private final View view;

    public ViewHolder(View view) {
        this.view = view;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public VM getViewModel() {
        return viewModel;
    }

    public final View getView() {
        return view;
    }

    @Override
    public void bindViewModel(VM viewModel) {

    }

    @Override
    public void bindActive(VM viewModel) {

    }
}
