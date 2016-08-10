package io.reactivesprint.android.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidLifecycleProviders;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.FetchedArrayViewBinder;
import io.reactivesprint.views.IFetchedArrayView;
import io.reactivesprint.views.IViewBinder;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class FetchedRecyclerFragment<E extends IAndroidViewModel, VM extends IFetchedArrayViewModel<E, ?, ?, ?> & IAndroidViewModel>
        extends RecyclerFragment<E, VM>
        implements IFetchedArrayView<E, VM> {
    private View refreshView;
    private View fetchNextView;
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getViewModel().getRefreshCommand().apply().subscribe();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fetched_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (refreshView == null) {
            refreshView = view.findViewById(R.id.refresh);
        }

        if (fetchNextView == null) {
            fetchNextView = view.findViewById(R.id.fetching_next);
        }

        if (refreshView instanceof SwipeRefreshLayout) {
            ((SwipeRefreshLayout) refreshView).setOnRefreshListener(refreshListener);
        }
    }

    @Override
    protected IViewBinder<VM> onCreateViewBinder() {
        return new FetchedArrayViewBinder<>(this, AndroidLifecycleProviders.from(this, FragmentEvent.START));
    }

    @Override
    public void presentRefreshing(boolean refreshing) {
        if (refreshView instanceof SwipeRefreshLayout) {
            ((SwipeRefreshLayout) refreshView).setRefreshing(refreshing);
        }
    }

    @Override
    public void presentFetchingNextPage(boolean fetchingNextPage) {
        fetchNextView.setVisibility(fetchingNextPage ? View.VISIBLE : View.GONE);
    }
}
