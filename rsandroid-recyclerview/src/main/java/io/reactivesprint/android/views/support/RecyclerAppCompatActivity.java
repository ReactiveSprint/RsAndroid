package io.reactivesprint.android.views.support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umove.android.R;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.ArrayActivity;
import io.reactivesprint.viewmodels.IArrayViewModel;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class RecyclerAppCompatActivity<VM extends IAndroidViewModel, AVM extends IArrayViewModel & IAndroidViewModel> extends ArrayActivity<VM, AVM> {
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = (RecyclerView) root.findViewById(android.R.id.list);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager();
        checkNotNull(layoutManager, "layoutManager");
        recyclerView.setLayoutManager(layoutManager);


        RecyclerView.Adapter adapter = onCreateAdapter(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return null;
    }

    @NonNull
    protected RecyclerView.Adapter onCreateAdapter(RecyclerView.LayoutManger layoutManger) {
        return null;
    }

    @Override
    public void onDataSetChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setLocalizedEmptyMessage(CharSequence localizedEmptyMessage) {

    }
}
