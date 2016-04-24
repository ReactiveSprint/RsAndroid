package io.reactivesprint.android.viewmodels;

import android.os.Parcel;
import android.test.AndroidTestCase;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class AndroidViewModelTest extends AndroidTestCase {

    public void testWriteToParcel() throws Exception {
        Parcel parcel = Parcel.obtain();
        AndroidViewModel viewModel = new AndroidViewModel(getContext());
        viewModel.getTitle().setValue("Test Title1");
        parcel.writeParcelable(viewModel, 0);

        // Must reset position to 0 for reading
        parcel.setDataPosition(0);
        AndroidViewModel viewModel1 = parcel.readParcelable(AndroidViewModel.class.getClassLoader());
        parcel.recycle();

        assertNotNull(viewModel1);
        assertEquals("Test Title1", viewModel1.getTitle().getValue());
    }
}
