package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import io.reactivesprint.android.models.TestAndroidModel;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class TestAndroidModelViewModel extends AndroidModelViewModel<TestAndroidModel> {

    public TestAndroidModelViewModel(@NonNull Context context, @NonNull TestAndroidModel model) {
        super(context, model);
    }

    protected TestAndroidModelViewModel(Parcel in) {
        super(in);
    }

    @Override
    protected ClassLoader getModelClassLoader() {
        return TestAndroidModel.class.getClassLoader();
    }

    public static Parcelable.Creator<TestAndroidModelViewModel> CREATOR = new Parcelable.Creator<TestAndroidModelViewModel>() {
        @Override
        public TestAndroidModelViewModel createFromParcel(Parcel source) {
            return new TestAndroidModelViewModel(source);
        }

        @Override
        public TestAndroidModelViewModel[] newArray(int size) {
            return new TestAndroidModelViewModel[0];
        }
    };
}
