package io.reactivesprint.android.viewmodels;

import android.os.Parcel;
import android.test.AndroidTestCase;

import io.reactivesprint.android.models.TestAndroidModel;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class AndroidModelViewModelTest extends AndroidTestCase {

    public void testWriteToParcel() throws Exception {
        TestAndroidModel model = new TestAndroidModel("TestModel");
        AndroidModelViewModel<TestAndroidModel> viewModel = new TestAndroidModelViewModel(getContext(), model);
        viewModel.getTitle().setValue("TestTitle");
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(viewModel, 0);

        // Must reset position to 0 for reading
        parcel.setDataPosition(0);
        AndroidModelViewModel<TestAndroidModel> viewModel1 = parcel.readParcelable(AndroidModelViewModel.class.getClassLoader());
        parcel.recycle();

        assertNotNull(viewModel1);
        assertNotNull(viewModel1.getModel());
        assertEquals("TestTitle", viewModel1.getTitle().getValue());
        assertEquals("TestModel", viewModel1.getModel().getTitle());
    }
}
