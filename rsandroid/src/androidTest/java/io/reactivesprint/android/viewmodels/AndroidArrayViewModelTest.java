package io.reactivesprint.android.viewmodels;

import android.os.Parcel;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class AndroidArrayViewModelTest extends AndroidTestCase {

    public List<AndroidViewModel> generateViewModels(int count) {
        List<AndroidViewModel> viewModels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AndroidViewModel viewModel = new AndroidViewModel(getContext());
            viewModel.title().setValue("Test" + i);
            viewModels.add(viewModel);
        }
        return viewModels;
    }

    public void testWriteToParcel() throws Exception {
        AndroidArrayViewModel<AndroidViewModel> viewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(3));
        viewModel.title().setValue("TestTitle");
        viewModel.localizedEmptyMessage().setValue("Message");

        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(viewModel, 0);

        // Must reset position to 0 for reading
        parcel.setDataPosition(0);
        AndroidArrayViewModel<AndroidViewModel> viewModel1 = parcel.readParcelable(TestAndroidArrayViewModel.class.getClassLoader());
        parcel.recycle();

        assertNotNull(viewModel1);
        assertEquals("TestTitle", viewModel1.title().getValue());
        assertEquals("Message", viewModel1.localizedEmptyMessage().getValue());
        assertEquals((Integer) 3, viewModel1.count().getValue());

        assertEquals("Test0", viewModel.getViewModel(0).title().getValue());
        assertEquals("Test1", viewModel.getViewModel(1).title().getValue());
        assertEquals("Test2", viewModel.getViewModel(2).title().getValue());
    }
}
