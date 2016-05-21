package io.reactivesprint.android.views.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import io.reactivesprint.android.R;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.android.views.AndroidViews;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.views.IFetchedArrayView;

/**
 * Created by Ahmad Baraka on 5/20/16.
 */
public class FetchedRecyclerFragment<VM extends IAndroidViewModel, AVM extends IFetchedArrayViewModel & IAndroidViewModel> extends RecyclerFragment<VM, AVM>
        implements IFetchedArrayView<VM, AVM>, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    protected SwipeRefreshLayout refreshLayout;
    @Nullable
    protected View fetchingNextPageView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = root.findViewById(R.id.refresh_layout);
        fetchingNextPageView = root.findViewById(R.id.fetching_next_page);

        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        AVM arrayViewModel = getArrayViewModel();
        bindFetchingNextPage(arrayViewModel);
        bindRefreshing(arrayViewModel);
    }

    @Override
    public void bindRefreshing(AVM arrayViewModel) {
        AndroidViews.bindRefreshing(this, arrayViewModel);
    }

    @Override
    public void presentRefreshing(boolean refreshing) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(refreshing);
        }
    }

    @Override
    public void bindFetchingNextPage(AVM arrayViewModel) {
        AndroidViews.bindFetchingNextPage(this, arrayViewModel);
    }

    @Override
    public void presentFetchingNextPage(boolean fetchingNextPage) {
        if (fetchingNextPageView != null) {
            if (fetchingNextPage) {
                fetchingNextPageView.setVisibility(View.VISIBLE);
            } else {
                fetchingNextPageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRefresh() {
        getArrayViewModel().getRefreshCommand().apply().subscribe();
    }
}
