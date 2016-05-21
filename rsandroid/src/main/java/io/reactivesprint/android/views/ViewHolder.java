package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.views.IViewHolder;

import static io.reactivesprint.Preconditions.checkNotNull;

public class ViewHolder<VM extends IAndroidViewModel> implements IViewHolder<VM> {
    //region Fields

    @NonNull
    private final View view;

    private VM viewModel;

    //endregion

    //region Constructors

    public ViewHolder(@NonNull View view) {
        checkNotNull(view, "view");
        this.view = view;
    }

    //endregion

    //region Properties

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public VM getViewModel() {
        return viewModel;
    }

    @NonNull
    public final View getView() {
        return view;
    }

    //endregion

    //region Binding

    @Override
    public void bindActive(VM viewModel) {

    }

    //endregion
}
