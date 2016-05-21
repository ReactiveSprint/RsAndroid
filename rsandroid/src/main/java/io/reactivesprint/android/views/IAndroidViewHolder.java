package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.views.IViewHolder;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public interface IAndroidViewHolder<VM extends IAndroidViewModel> extends IViewHolder<VM> {
    /**
     * @return A {@link View} associated with the receiver.
     */
    @NonNull
    View getView();
}
