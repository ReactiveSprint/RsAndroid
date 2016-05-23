package io.reactivesprint.android.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import rx.functions.Func2;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
public class RsRecyclerAdapter<E extends IAndroidViewModel, VM extends IArrayViewModel<E> & IAndroidViewModel, VH extends RsRecyclerViewHolder<E>>
        extends RecyclerView.Adapter<VH> {
    //region Fields

    @NonNull
    private final VM arrayViewModel;

    @NonNull
    private final ViewHolderCreator<E, VH> onCreateViewHolder;

    //endregion

    //region Constructors

    public static <E extends IAndroidViewModel, VM extends IArrayViewModel<E> & IAndroidViewModel>
    RsRecyclerAdapter<E, VM, RsRecyclerViewHolder<E>> create(@NonNull VM arrayViewModel) {
        checkNotNull(arrayViewModel, "arrayViewModel");
        return new RsRecyclerAdapter<>(arrayViewModel, new ViewHolderCreator<E, RsRecyclerViewHolder<E>>() {
            @Override
            public RsRecyclerViewHolder<E> call(ViewGroup viewGroup, Integer integer) {
                return new RsRecyclerViewHolder<>(viewGroup.getContext());
            }
        });
    }

    public RsRecyclerAdapter(@NonNull VM arrayViewModel, @NonNull ViewHolderCreator<E, VH> onCreateViewHolder) {
        checkNotNull(arrayViewModel, "arrayViewModel");
        checkNotNull(onCreateViewHolder, "onCreateViewHolder");
        this.arrayViewModel = arrayViewModel;
        this.onCreateViewHolder = onCreateViewHolder;
    }

    //endregion

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder.call(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setViewModel(arrayViewModel.getViewModel(position));
    }

    @Override
    public int getItemCount() {
        return arrayViewModel.count().getValue();
    }

    //region ViewHolderCreator

    public interface ViewHolderCreator<E extends IAndroidViewModel, VH extends RsRecyclerViewHolder<E>>
            extends Func2<ViewGroup, Integer, VH> {
    }

    //endregion
}
