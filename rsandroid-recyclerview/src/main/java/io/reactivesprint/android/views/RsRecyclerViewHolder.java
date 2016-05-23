package io.reactivesprint.android.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;
import io.reactivesprint.views.ViewBinder;
import rx.Observable;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
public class RsRecyclerViewHolder<VM extends IAndroidViewModel>
        extends RecyclerView.ViewHolder implements IAndroidViewHolder<VM> {
    //region Fields

    private IViewBinder<VM> viewBinder;

    private TextView titleTextView;

    private final IMutableProperty<VM> viewModel = new MutableProperty<>(null);

    //endregion

    //region Constructors

    private static TextView createTextView(@NonNull Context context) {
        checkNotNull(context, "context");
        TextView textView = new TextView(context);
        textView.setId(io.reactivesprint.android.R.id.title);
        return textView;
    }

    /**
     * Creates an instance with a {@link #getTitleTextView()}
     */
    public RsRecyclerViewHolder(@NonNull Context context) {
        this(createTextView(context));
    }

    /**
     * Creates an instance with {@code view}
     *
     * @param view View used in this receiver.
     *             Your view may contain a {@link TextView} with id {@code R.id.title}
     */
    public RsRecyclerViewHolder(@NonNull View view) {
        super(view);
        onViewCreated(view);
        viewBinder = onCreateViewBinder();
    }

    //endregion

    //region Lifecycle

    /**
     * You may override this method and use {@link View#findViewById(int)}.
     */
    protected void onViewCreated(View view) {
        View titleView = view.findViewById(io.reactivesprint.android.R.id.title);

        if (titleView == null) {
            return;
        }

        if (!(titleView instanceof TextView)) {
            throw new RuntimeException("title is expected to be TextView but was "
                    + titleView.getClass().getName() + " instead.");
        }

        titleTextView = (TextView) titleView;
    }

    protected IViewBinder<VM> onCreateViewBinder() {
        return new ViewBinder<>(this, AndroidLifecycleProvider.from(this));
    }

    //endregion

    //region Properties

    public void setViewModel(VM viewModel) {
        this.viewModel.setValue(viewModel);
    }

    public VM getViewModel() {
        return viewModel.getValue();
    }

    @NonNull
    @Override
    public final View getView() {
        return itemView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public IViewBinder<VM> getViewBinder() {
        return viewBinder;
    }

    @NonNull
    @Override
    public Observable<VM> onViewRecycled() {
        return viewModel.getObservable();
    }

    //endregion

    //region IView

    @Override
    public void setTitle(CharSequence title) {
        if (getTitleTextView() != null) {
            getTitleTextView().setText(title);
        }
    }

    @Override
    public void presentLoading(boolean loading) {

    }

    @Override
    public void presentError(IViewModelException error) {

    }

    //endregion
}
