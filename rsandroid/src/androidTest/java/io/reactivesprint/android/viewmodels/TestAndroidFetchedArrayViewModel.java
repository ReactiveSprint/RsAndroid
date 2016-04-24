package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivesprint.rx.Pair;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class TestAndroidFetchedArrayViewModel extends AndroidFetchedArrayViewModel<AndroidViewModel, Integer> {

    public TestAndroidFetchedArrayViewModel(@NonNull Context context) {
        super(context);
    }

    protected TestAndroidFetchedArrayViewModel(Parcel in) {
        super(in);
    }

    @Override
    protected ClassLoader getArrayClassLoader() {
        return AndroidViewModel.class.getClassLoader();
    }

    @Override
    protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
        return Observable.just(new Pair<Integer, Collection<AndroidViewModel>>(1, generateViewModels(3)));
    }

    @SuppressWarnings("ConstantConditions")
    public List<AndroidViewModel> generateViewModels(int count) {
        List<AndroidViewModel> viewModels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AndroidViewModel viewModel = new AndroidViewModel(getContext());
            viewModel.getTitle().setValue("Test" + i);
            viewModels.add(viewModel);
        }
        return viewModels;
    }

    public static Creator<TestAndroidFetchedArrayViewModel> CREATOR = new Creator<TestAndroidFetchedArrayViewModel>() {
        @Override
        public TestAndroidFetchedArrayViewModel createFromParcel(Parcel source) {
            return new TestAndroidFetchedArrayViewModel(source);
        }

        @Override
        public TestAndroidFetchedArrayViewModel[] newArray(int size) {
            return new TestAndroidFetchedArrayViewModel[0];
        }
    };
}
