package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public abstract class AndroidMutableArrayViewModel<E extends IAndroidViewModel> extends AndroidArrayViewModel<E> {
    public AndroidMutableArrayViewModel(@NonNull Context context, @NonNull Collection<E> viewModels) {
        super(context, viewModels);
    }

    protected AndroidMutableArrayViewModel(Parcel in) {
        super(in);
    }

    @Override
    public void setViewModels(Collection<E> viewModels) {
        super.setViewModels(viewModels);
    }
}
