package io.reactivesprint.android.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import rx.functions.Func2;

public class RsAdapter<E extends IViewModel, AVM extends IArrayViewModel<E>> extends BaseAdapter {
    private final AVM arrayViewModel;
    private final Func2<Integer, ViewGroup, ViewHolder<E>> onCreateViewHolder;

    public RsAdapter(AVM arrayViewModel, Func2<Integer, ViewGroup, ViewHolder<E>> onCreateViewHolder) {
        this.arrayViewModel = arrayViewModel;
        this.onCreateViewHolder = onCreateViewHolder;
    }

    @Override
    public int getCount() {
        return arrayViewModel.getCount().getValue();
    }

    @Override
    public E getItem(int position) {
        return arrayViewModel.getViewModel(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder<E> viewHolder;

        if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof ViewHolder) {
            //noinspection unchecked
            viewHolder = (ViewHolder<E>) convertView.getTag();
        } else {
            viewHolder = onCreateViewHolder.call(position, parent);
        }

        onBindViewHolder(viewHolder, position);

        return viewHolder.getView();
    }

    public void onBindViewHolder(ViewHolder<E> viewHolder, int position) {
        viewHolder.setViewModel(getItem(position));
    }
}