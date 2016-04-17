package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivesprint.viewmodels.IViewModel;

/**
 * Created by Ahmad Baraka on 4/14/16.
 * <p/>
 * Android ViewModels wrap {@link Context} and are {@link Parcelable}.
 * <p/>
 * Use {@link #setContext(Context)} if the receiver was initialized from {@link android.os.Parcel}
 */
public interface IAndroidViewModel extends IViewModel, Parcelable {
    /**
     * @return Wrapped {@link Context} in the receiver.
     */
    @Nullable
    Context getContext();

    /**
     * Sets {@link Context} for the receiver.
     */
    void setContext(@NonNull Context context);
}
