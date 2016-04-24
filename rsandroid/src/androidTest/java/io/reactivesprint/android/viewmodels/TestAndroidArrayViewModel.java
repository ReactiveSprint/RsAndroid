package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class TestAndroidArrayViewModel extends AndroidArrayViewModel<AndroidViewModel> {

    public static Creator<TestAndroidArrayViewModel> CREATOR = new Creator<TestAndroidArrayViewModel>() {
        @Override
        public TestAndroidArrayViewModel createFromParcel(Parcel source) {
            return new TestAndroidArrayViewModel(source);
        }

        @Override
        public TestAndroidArrayViewModel[] newArray(int size) {
            return new TestAndroidArrayViewModel[0];
        }
    };

    public TestAndroidArrayViewModel(@NonNull Context context, @NonNull Collection<AndroidViewModel> viewModels) {
        super(context, viewModels);
    }

    protected TestAndroidArrayViewModel(Parcel in) {
        super(in);
    }

    @Override
    protected ClassLoader getArrayClassLoader() {
        return AndroidViewModel.class.getClassLoader();
    }
}
