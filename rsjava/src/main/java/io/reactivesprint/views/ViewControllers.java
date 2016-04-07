package io.reactivesprint.views;

import io.reactivesprint.viewmodels.IViewModelException;
import rx.functions.Action1;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Static Factory methods for creating {@link Action1} for
 * {@link IViewController}, {@link IArrayViewController} and {@link IFetchedArrayViewController}.
 */
public final class ViewControllers {
    private ViewControllers() {
        throw new IllegalStateException("no instances.");
    }

    /**
     * Create an action which invokes {@link IViewController#setTitle(CharSequence)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<CharSequence> setTitle(final IViewController<?> viewController) {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                viewController.setTitle(s);
            }
        };
    }

    /**
     * Create an action which invokes {@link IViewController#presentLoading(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<Boolean> presentLoading(final IViewController<?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentLoading(aBoolean);
            }
        };
    }

    /**
     * Create an action which invokes {@link IViewController#presentError(IViewModelException)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<IViewModelException> presentError(final IViewController<?> viewController) {
        return new Action1<IViewModelException>() {
            @Override
            public void call(IViewModelException error) {
                viewController.presentError(error);
            }
        };
    }

    /**
     * Create an action which invokes {@link IArrayViewController#onDataSetChanged()}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<Integer> onDataSetChanged(final IArrayViewController<?, ?> viewController) {
        return new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                viewController.onDataSetChanged();
            }
        };
    }

    /**
     * Create an action which invokes {@link IArrayViewController#setLocalizedEmptyMessage(CharSequence)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<CharSequence> setLocalizedEmptyMessage(final IArrayViewController<?, ?> viewController) {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                viewController.setLocalizedEmptyMessage(charSequence);
            }
        };
    }

    /**
     * Create an action which invokes {@link IFetchedArrayViewController#presentRefreshing(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<Boolean> presentRefreshing(final IFetchedArrayViewController<?, ?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentRefreshing(aBoolean);
            }
        };
    }

    /**
     * Create an action which invokes {@link IFetchedArrayViewController#presentFetchingNextPage(boolean)}
     * <p/>
     * <em>Note:</em>  The created action keeps a strong reference to {@code viewController},
     * so when this is used with {@link rx.Observable}, unsubscribe
     * to free this reference.
     */
    public static Action1<Boolean> presentFetchingNextPage(final IFetchedArrayViewController<?, ?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentFetchingNextPage(aBoolean);
            }
        };
    }
}
