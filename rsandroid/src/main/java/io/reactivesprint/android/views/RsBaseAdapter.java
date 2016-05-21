package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import io.reactivesprint.android.R;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import rx.functions.Func2;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Implementation of {@link BaseAdapter} with an {@link IAndroidViewHolder}
 * <p/>
 * You don't need to override this class,
 * instead you can use {@code onCreateViewHolder} to create an {@link IAndroidViewHolder} as needed.
 */
public class RsBaseAdapter<E extends IAndroidViewModel, AVM extends IArrayViewModel<E> & IAndroidViewModel> extends BaseAdapter {
    //region Fields

    @NonNull
    private final AVM arrayViewModel;

    @NonNull
    private final Func2<Integer, ViewGroup, IAndroidViewHolder<E>> onCreateViewHolder;

    //endregion

    //region Constructors

    public RsBaseAdapter(@NonNull AVM arrayViewModel, @NonNull Func2<Integer, ViewGroup, IAndroidViewHolder<E>> onCreateViewHolder) {
        checkNotNull(arrayViewModel, "arrayViewModel");
        checkNotNull(onCreateViewHolder, "onCreateViewHolder");
        this.arrayViewModel = arrayViewModel;
        this.onCreateViewHolder = onCreateViewHolder;
    }

    //endregion

    //region BaseAdapter

    @Override
    public int getCount() {
        return arrayViewModel.count().getValue();
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
        IAndroidViewHolder<E> viewHolder = onCreateViewHolder(position, convertView, parent);

        onBindViewHolder(viewHolder, position);

        return viewHolder.getView();
    }

    @SuppressWarnings("unchecked")
    public IAndroidViewHolder<E> onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        IAndroidViewHolder<E> viewHolder;

        if (convertView instanceof IAndroidViewHolder) {
            viewHolder = (IAndroidViewHolder<E>) convertView;
        } else if (convertView != null && convertView.getTag(R.id.view_holder_id) instanceof IAndroidViewHolder) {
            viewHolder = (IAndroidViewHolder<E>) convertView.getTag(R.id.view_holder_id);
        } else {
            viewHolder = onCreateViewHolder.call(position, parent);
            viewHolder.getView().setTag(R.id.view_holder_id, viewHolder);
        }

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull IAndroidViewHolder<E> viewHolder, int position) {
        viewHolder.setViewModel(getItem(position));
    }

    //endregion
}
