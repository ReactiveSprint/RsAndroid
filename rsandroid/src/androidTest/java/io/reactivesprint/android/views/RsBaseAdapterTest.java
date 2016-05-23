package io.reactivesprint.android.views;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivesprint.android.viewmodels.AndroidArrayViewModel;
import io.reactivesprint.android.viewmodels.AndroidViewModel;
import io.reactivesprint.android.viewmodels.TestAndroidArrayViewModel;

/**
 * Created by Ahmad Baraka on 5/23/16.
 */
public class RsBaseAdapterTest extends AndroidTestCase {

    public static List<AndroidViewModel> generateViewModels(Context context, int count) {
        List<AndroidViewModel> viewModels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AndroidViewModel viewModel = new AndroidViewModel(context);
            viewModel.title().setValue("Test" + i);
            viewModels.add(viewModel);
        }
        return viewModels;
    }

    AndroidArrayViewModel<AndroidViewModel> arrayViewModel;
    RsBaseAdapter<AndroidViewModel, AndroidArrayViewModel<AndroidViewModel>> adapter;
    ViewHolder<AndroidViewModel> viewHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(getContext(), 3));
        adapter = new RsBaseAdapter<>(arrayViewModel);
        viewHolder = new ViewHolder<>(getContext());
    }

    public void testGetCount() throws Exception {
        assertEquals(3, adapter.getCount());
    }

    public void testGetItem() throws Exception {
        assertSame(arrayViewModel.getViewModel(0), adapter.getItem(0));
    }

    public void testOnCreateViewHolder() throws Exception {
        IAndroidViewHolder<AndroidViewModel> viewHolder = adapter.onCreateViewHolder(0, null, new ListView(getContext()));
        assertSame(viewHolder.getClass(), ViewHolder.class);
    }

    public void testOnBindViewHolder() throws Exception {
        ViewHolder<AndroidViewModel> viewHolder = new ViewHolder<>(getContext());
        adapter.onBindViewHolder(viewHolder, 0);
        assertSame(viewHolder.getViewModel(), adapter.getItem(0));
    }
}
