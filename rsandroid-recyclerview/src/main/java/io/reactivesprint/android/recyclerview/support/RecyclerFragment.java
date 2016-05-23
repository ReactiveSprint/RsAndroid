package io.reactivesprint.android.recyclerview.support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.recyclerview.R;
import io.reactivesprint.android.recyclerview.RsRecyclerAdapter;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProvider;
import io.reactivesprint.android.views.RsFragment;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.ArrayViewBinder;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IViewBinder;

import static io.reactivesprint.Preconditions.checkNotNullWithMessage;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class RecyclerFragment<E extends IAndroidViewModel, VM extends IArrayViewModel<E> & IAndroidViewModel>
        extends RsFragment<VM>
        implements IArrayView<E, VM> {
    //region Fields

    protected RecyclerView recyclerView;
    protected View emptyView;

    //endregion

    //region Create View

    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new ArrayViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = (RecyclerView) root.findViewById(android.R.id.list);
        emptyView = root.findViewById(android.R.id.empty);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkNotNullWithMessage(recyclerView, "recyclerView must be inflated in your layout.");

        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = onCreateAdapter(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    //endregion

    @NonNull
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @NonNull
    protected RecyclerView.Adapter onCreateAdapter(RecyclerView.LayoutManager layoutManger) {
        return RsRecyclerAdapter.create(getViewModel());
    }

    @Override
    public void onDataSetChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setLocalizedEmptyMessage(CharSequence localizedEmptyMessage) {
        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(localizedEmptyMessage);
        }
    }

    @Override
    public void setLocalizedEmptyMessageVisibility(boolean visibility) {
        if (emptyView == null) {
            return;
        }
        emptyView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
