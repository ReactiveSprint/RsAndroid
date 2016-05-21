package io.reactivesprint.android.views.support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.reactivesprint.android.R;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class RecyclerFragment<VM extends IAndroidViewModel, AVM extends IArrayViewModel & IAndroidViewModel> extends ArrayFragment<VM, AVM> {
    protected RecyclerView recyclerView;
    @Nullable
    protected View emptyMessageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = (RecyclerView) root.findViewById(android.R.id.list);
        emptyMessageView = root.findViewById(R.id.empty_message);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkNotNull(recyclerView, "recyclerView");

        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager();
        checkNotNull(layoutManager, "layoutManager");
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = onCreateAdapter(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @NonNull
    protected RecyclerView.Adapter onCreateAdapter(RecyclerView.LayoutManager layoutManger) {
        return null;
    }

    @Override
    public void onDataSetChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setLocalizedEmptyMessage(CharSequence localizedEmptyMessage) {
        if (emptyMessageView instanceof TextView) {
            ((TextView) emptyMessageView).setText(localizedEmptyMessage);
        }
    }

    @Override
    public void setLocalizedEmptyMessageVisibility(boolean visibility) {
        if (emptyMessageView != null) {
            if (visibility) {
                emptyMessageView.setVisibility(View.VISIBLE);
            } else {
                emptyMessageView.setVisibility(View.GONE);
            }
        }
    }
}
