package io.reactivesprint.android.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import io.reactivesprint.android.R;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.MutableProperty;
import rx.Observable;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Implementation of {@link IAndroidViewHolder}
 */
public class ViewHolder<VM extends IAndroidViewModel> implements IAndroidViewHolder<VM> {
    //region Fields

    @NonNull
    private final View view;

    private TextView titleTextView;

    private final IMutableProperty<VM> viewModel = new MutableProperty<>(null);

    //endregion

    //region Constructors

    private static TextView createTextView(@NonNull Context context) {
        checkNotNull(context, "context");
        TextView textView = new TextView(context);
        textView.setId(R.id.title);
        return textView;
    }

    /**
     * Creates an instance with a {@link #getTitleTextView()}
     */
    public ViewHolder(@NonNull Context context) {
        this(createTextView(context));
    }

    /**
     * Creates an instance with {@code view}
     *
     * @param view View used in this receiver.
     *             Your view may contain a {@link TextView} with id {@code R.id.title}
     */
    public ViewHolder(@NonNull View view) {
        checkNotNull(view, "view");
        this.view = view;
        onViewCreated(view);
    }

    /**
     * You may override this method and use {@link View#findViewById(int)}.
     */
    protected void onViewCreated(View view) {
        View titleView = view.findViewById(R.id.title);

        if (titleView == null) {
            return;
        }

        if (!(titleView instanceof TextView)) {
            throw new RuntimeException("title is expected to be TextView but was "
                    + titleView.getClass().getName() + " instead.");
        }

        titleTextView = (TextView) titleView;
    }

    //endregion

    //region Properties

    public void setViewModel(VM viewModel) {
        this.viewModel.setValue(viewModel);

        if (viewModel == null) {
            return;
        }

        if (titleTextView != null) {
            viewModel.title().getObservable()
                    .takeUntil(getUntilObservable())
                    .subscribe(RxTextView.text(titleTextView));
        }
    }

    public VM getViewModel() {
        return viewModel.getValue();
    }

    @NonNull
    @Override
    public final View getView() {
        return view;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public Observable<Void> getUntilObservable() {
        return viewModel.getObservable().map(new Func1<VM, Void>() {
            @Override
            public Void call(VM vm) {
                return null;
            }
        });
    }

    //endregion

    //region Binding

    @Override
    public void bindActive(VM viewModel) {

    }

    //endregion
}
