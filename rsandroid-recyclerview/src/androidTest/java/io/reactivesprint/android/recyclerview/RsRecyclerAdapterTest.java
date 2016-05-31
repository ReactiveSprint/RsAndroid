package io.reactivesprint.android.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivesprint.android.viewmodels.AndroidArrayViewModel;
import io.reactivesprint.android.viewmodels.AndroidViewModel;
import io.reactivesprint.android.views.IAndroidViewHolder;
import io.reactivesprint.android.views.ViewHolder;

/**
 * Created by Ahmad Baraka on 6/1/16.
 */
public class RsRecyclerAdapterTest extends AndroidTestCase {

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
    RsRecyclerAdapter<AndroidViewModel, AndroidArrayViewModel<AndroidViewModel>, RsRecyclerViewHolder<AndroidViewModel>> adapter;
    RsRecyclerViewHolder<AndroidViewModel> viewHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(getContext(), 3));
        adapter = RsRecyclerAdapter.create(arrayViewModel);
        viewHolder = new RsRecyclerViewHolder<>(getContext());
    }

    public void testGetCount() throws Exception {
        assertEquals(3, adapter.getItemCount());
    }

    public void testOnCreateViewHolder() throws Exception {
        IAndroidViewHolder<AndroidViewModel> viewHolder = adapter.onCreateViewHolder(new RecyclerView(getContext()), 0);
        assertSame(viewHolder.getClass(), RsRecyclerViewHolder.class);
    }

    public void testOnBindViewHolder() throws Exception {
        RsRecyclerViewHolder<AndroidViewModel> viewHolder = new RsRecyclerViewHolder<>(getContext());
        adapter.onBindViewHolder(viewHolder, 0);
        assertSame(viewHolder.getViewModel(), arrayViewModel.getViewModel(0));
    }
}
