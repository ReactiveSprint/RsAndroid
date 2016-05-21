package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.IViewModelException;
import rx.functions.Action1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Static Factory methods for creating {@link Action1} for
 * {@link IView}, {@link IArrayView} and {@link IFetchedArrayView}.
 */
public final class Views {
    private Views() {
        throw new AssertionError("No instances.");
    }

    /**
     * Create an action which invokes {@link IView#setTitle(CharSequence)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel> Action1<CharSequence> setTitle(final IView<VM> view) {
        checkNotNull(view, "view");
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                view.setTitle(s);
            }
        };
    }

    /**
     * Create an action which invokes {@link IView#presentLoading(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel> Action1<Boolean> presentLoading(final IView<VM> view) {
        checkNotNull(view, "view");
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                view.presentLoading(aBoolean);
            }
        };
    }

    /**
     * Create an action which invokes {@link IView#presentError(IViewModelException)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel> Action1<IViewModelException> presentError(final IView<VM> view) {
        checkNotNull(view, "view");
        return new Action1<IViewModelException>() {
            @Override
            public void call(IViewModelException error) {
                view.presentError(error);
            }
        };
    }

    /**
     * Create an action which invokes {@link IArrayView#onDataSetChanged()}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel, AVM extends IArrayViewModel<?>> Action1<Integer> onDataSetChanged(final IArrayView<VM, AVM> view) {
        checkNotNull(view, "view");
        return new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                view.onDataSetChanged();
            }
        };
    }

    /**
     * Create an action which invokes {@link IArrayView#setLocalizedEmptyMessage(CharSequence)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel, AVM extends IArrayViewModel<?>> Action1<CharSequence> setLocalizedEmptyMessage(final IArrayView<VM, AVM> view) {
        checkNotNull(view, "view");
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                view.setLocalizedEmptyMessage(charSequence);
            }
        };
    }

    /**
     * Create an action which invokes {@link IArrayView#setLocalizedEmptyMessageVisibility(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel, AVM extends IArrayViewModel<?>> Action1<Boolean> setLocalizedEmptyMessageVisibility(final IArrayView<VM, AVM> view) {
        checkNotNull(view, "view");
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                view.setLocalizedEmptyMessageVisibility(aBoolean);
            }
        };
    }

    /**
     * Create an action which invokes {@link IFetchedArrayView#presentRefreshing(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel, AVM extends IFetchedArrayViewModel<?, ?, ?, ?>> Action1<Boolean> presentRefreshing(final IFetchedArrayView<VM, AVM> view) {
        checkNotNull(view, "view");
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                view.presentRefreshing(aBoolean);
            }
        };
    }

    /**
     * Create an action which invokes {@link IFetchedArrayView#presentFetchingNextPage(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code view},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static <VM extends IViewModel, AVM extends IFetchedArrayViewModel<?, ?, ?, ?>> Action1<Boolean> presentFetchingNextPage(final IFetchedArrayView<VM, AVM> view) {
        checkNotNull(view, "view");
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                view.presentFetchingNextPage(aBoolean);
            }
        };
    }
}
