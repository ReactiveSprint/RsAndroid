package io.reactivesprint.android.viewmodels;

import android.os.Parcel;
import android.test.AndroidTestCase;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class AndroidFetchedArrayViewModelTest extends AndroidTestCase {

    public void testWriteToParcel() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> viewModel = new TestAndroidFetchedArrayViewModel(getContext());
        viewModel.getTitle().setValue("TestTitle");
        viewModel.getLocalizedEmptyMessage().setValue("Message");

        viewModel.getFetchCommand().apply().subscribe();

        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(viewModel, 0);

        // Must reset position to 0 for reading
        parcel.setDataPosition(0);
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> viewModel1 = parcel.readParcelable(TestAndroidFetchedArrayViewModel.class.getClassLoader());
        parcel.recycle();

        assertNotNull(viewModel1);
        assertEquals("TestTitle", viewModel1.getTitle().getValue());
        assertEquals("Message", viewModel1.getLocalizedEmptyMessage().getValue());
        assertEquals((Integer) 3, viewModel1.getCount().getValue());
        assertEquals((Integer) 1, viewModel1.getNextPage());

        assertEquals("Test0", viewModel.getViewModel(0).getTitle().getValue());
        assertEquals("Test1", viewModel.getViewModel(1).getTitle().getValue());
        assertEquals("Test2", viewModel.getViewModel(2).getTitle().getValue());
    }
}
