package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import io.reactivesprint.android.models.IAndroidModel;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public abstract class AndroidMutableModelViewModel<M extends IAndroidModel> extends AndroidModelViewModel<M> {
    public AndroidMutableModelViewModel(@NonNull Context context, @NonNull M model) {
        super(context, model);
    }

    protected AndroidMutableModelViewModel(Parcel in) {
        super(in);
    }

    @Override
    public void setModel(M model) {
        super.setModel(model);
    }
}
